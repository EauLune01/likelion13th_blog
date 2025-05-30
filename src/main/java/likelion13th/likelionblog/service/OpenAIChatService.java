package likelion13th.likelionblog.service;

import likelion13th.likelionblog.dto.request.ChatRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAIChatService {

    //DI:OpenAIConfig에서 빈으로 등록한 OpenAiService 인스턴스를 주입받는다.
    private final OpenAiService openAiService;

    public String getChatResponse(ChatRequest chatRequest){
        String question=chatRequest.getPrompt();

        //OpenAI API에 보낼 메시지 리스트에 추가할 ChatMessage 객체를 생성한다.
        // role은 "user"로 설정하고, content는 요청으로 들어온 사용자 질문.
        //ChatMessage: OpenAI의 Java 라이브러리(com.theokanning.openai 같은 패키지)에서 챗봇 메시지를 나타내는 클래스
        ChatMessage userMessage=new ChatMessage("user",question);

        /*role
        system: 모델에게 행동 지침을 주는 메시지
        user: 사용자가 실제로 입력하는 내용
        assistant: AI가 이전에 응답한 내용 */

        //OpenAI API에 보낼 챗 완성 요청을 생성한다.
        //ChatCompletionRequest: OpenAI 공식 Java SDK에서 제공하는 챗봇 완성 요청(request)용 DTO
        ChatCompletionRequest request= ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")      //사용할 OpenAI 모델 지정
                .messages(List.of(userMessage)) //챗봇에게 보낼 메시지 리스트 설정
                .temperature(0.7)  //응답의 창의성을 조정. 0~1. 값이 높을수록 예측불가능한 응답 생성
                .build();
        try {
            //OpenAiService를 사용하여 OpenAI API를 호출하고 챗봇 응답을 받는 부분
            ChatCompletionResult result = openAiService.createChatCompletion(request);

            //챗봇 응답 결과에서 메시지 내용을 추출하여 반환한다.
            //받은 결과 중 첫 번째 응답에서 메시지 객체를 꺼내고 메시지 내용을 추출해서 return
            return result.getChoices().get(0).getMessage().getContent();

        } catch (Exception e) {
            throw new RuntimeException("OpenAI API 호출 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }
}

