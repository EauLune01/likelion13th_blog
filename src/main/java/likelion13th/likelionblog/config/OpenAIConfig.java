package likelion13th.likelionblog.config;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//스프링이 이 클래스를 읽어서 내부에 선언된 @Bean 메서드를 찾아 실행
public class OpenAIConfig {

    //application.yml파일에 설정된 openai.api.key값을 openaikey필드에 주입
    @Value("${openai.api.key}") //Spring의 프로퍼티 값 주입 어노테이션
    private String openaikey;

    //OpenAiService 인스턴스를 생성하고 openaikey를 사용해서 초기화한다.
    //OpenAiService: OpenAI Java SDK에서 제공하는 서비스 클래스
    @Bean //주로 직접 인스턴스를 생성하거나, 외부 라이브러리 클래스 등을 빈으로 등록할 때 사용
    public OpenAiService openAiService(){
        return new OpenAiService(openaikey);
    }
}
