package project.study.controller.api.kakaologin;

import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.study.domain.KakaoToken;
import project.study.domain.Member;
import project.study.domain.Social;
import project.study.dto.login.requestdto.RequestSocialLoginDto;
import project.study.enums.SocialEnum;
import project.study.jpaRepository.SocialJpaRepository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
@Slf4j
public class KakaoLoginRepository {

    private final SocialJpaRepository socialJpaRepository;

    @Value("${kakaoLogin.restApi}")
    private String restApiKey;

    public KakaoToken getKakaoAccessToken(String code) {
        String accessToken = "";
        String refreshToken = "";
        String requestURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            // setDoOutput()은 OutputStream으로 POST 데이터를 넘겨 주겠다는 옵션이다.
            // POST 요청을 수행하려면 setDoOutput()을 true로 설정한다.
            conn.setDoOutput(true);

            // POST 요청에서 필요한 파라미터를 OutputStream을 통해 전송
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            String sb = "grant_type=authorization_code" +
                    "&client_id=" + restApiKey + // REST_API_KEY
                    "&redirect_uri=http://localhost:8080/login/kakao" + // REDIRECT_URI
                    "&code=" + code;
            bufferedWriter.write(sb);
            bufferedWriter.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            // 요청을 통해 얻은 데이터를 InputStreamReader을 통해 읽어 오기
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body : " + result);

            JsonElement element = JsonParser.parseString(result.toString());

            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("accessToken : " + accessToken);
            System.out.println("refreshToken : " + refreshToken);

            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new KakaoToken(accessToken, refreshToken);
    }

    public void getUserInfo(RequestSocialLoginDto data, KakaoToken kakaoToken) {
        String postURL = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(postURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Authorization", "Bearer " + kakaoToken.getAccess_token());

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body : " + result);

            JsonElement element = JsonParser.parseString(result.toString());
            String id = element.getAsJsonObject().get("id").getAsString();

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String name = kakaoAccount.getAsJsonObject().get("name").getAsString();
            String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
            String phone = kakaoAccount.getAsJsonObject().get("phone_number").getAsString();

            data.setName(name);
            data.setSocialEnum(SocialEnum.KAKAO);
            data.setNickName(nickname);
            data.setEmail(email);
            data.setPhone(phone);
            data.setToken(kakaoToken);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<Social> findBySocialAndEmail(SocialEnum socialEnum, String email) {
        if (email == null) return Optional.empty();
        return socialJpaRepository.findBySocialTypeAndSocialEmail(socialEnum, email);
    }

    @Transactional
    public void updateKakaoToken(Member loginMember, KakaoToken newToken) {
        KakaoToken kakaoToken = loginMember.getSocial().getToken();
        if (kakaoToken.getAccess_token() == null) kakaoToken.setAccess_token(newToken.getAccess_token());
        if (kakaoToken.getRefresh_token() == null) kakaoToken.setRefresh_token(newToken.getRefresh_token());
        String postURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(postURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // POST 요청에 필요한 파라미터를 OutputStream을 통해 전송
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            String sb = "grant_type=refresh_token" +
                "&client_id=" + restApiKey + // REST_API_KEY
                "&refresh_token=" + kakaoToken.getRefresh_token(); // REFRESH_TOKEN
            bufferedWriter.write(sb);
            bufferedWriter.flush();

            // 요청을 통해 얻은 데이터를 InputStreamReader을 통해 읽어 오기
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body : " + result);

            JsonElement element = JsonParser.parseString(result.toString());

            Set<String> keySet = element.getAsJsonObject().keySet();

            // 새로 발급 받은 accessToken 불러오기
            String accessToken = element.getAsJsonObject().get("access_token").getAsString();
            // refreshToken은 유효 기간이 1개월 미만인 경우에만 갱신되어 반환되므로,
            // 반환되지 않는 경우의 상황을 if문으로 처리해주었다.

            String refreshToken = "";
            if(keySet.contains("refresh_token")) {
                refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();
            }

            kakaoToken.setAccess_token(accessToken);
            if(!refreshToken.equals("")) {
                kakaoToken.setRefresh_token(refreshToken);
            }

            bufferedReader.close();
            bufferedWriter.close();

            log.info("KakaoToken Update : access_token : {}, refresh_token : {}", accessToken, refreshToken);

        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

}