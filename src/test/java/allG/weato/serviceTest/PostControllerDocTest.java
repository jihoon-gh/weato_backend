/*
package allG.weato.serviceTest;

import allG.weato.domain.Post;
import allG.weato.domain.enums.BoardType;
import allG.weato.dto.post.create.CreatePostResponse;
import allG.weato.domains.post.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static allG.weato.domain.enums.BoardType.EXPERIENCEINFO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
public class PostControllerDocTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Test
    @DisplayName("post 조회 테스트")
    public void test1() throws Exception{

        Post post = new Post();
        post.changeTitle("test");
        post.changeContent("content");
        postService.join(post);

        when(postService.join(any())).thenReturn(CreatePostResponse);

        this.mockMvc.perform(post("/api/posts")
                        .content("{\"title\": \"title\", \n\"content\": \"content\"}")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated())
                        .andDo(document("post-create",
                                requestFields(
                                        fieldWithPath("title").description("Post 제목"),
                                        fieldWithPath("content").description("Post 내용").optional()
                                ))
                        );
    }
}
*/
