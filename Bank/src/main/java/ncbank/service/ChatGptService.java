/*
 * package ncbank.service;
 * 
 * import com.fasterxml.jackson.databind.ObjectMapper; import
 * com.fasterxml.jackson.databind.node.ObjectNode; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.stereotype.Service; import
 * org.springframework.web.client.RestTemplate; import
 * org.springframework.http.HttpHeaders; import
 * org.springframework.http.HttpEntity; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.http.HttpMethod; import
 * org.springframework.http.MediaType;
 * 
 * @Service public class ChatGptService {
 * 
 * @Value("${openai.api.key}") private String apiKey;
 * 
 * @Value("${openai.model}") private String model;
 * 
 * private final RestTemplate restTemplate; private final ObjectMapper
 * objectMapper;
 * 
 * public ChatGptService(RestTemplate restTemplate, ObjectMapper objectMapper) {
 * this.restTemplate = restTemplate; this.objectMapper = objectMapper; }
 * 
 * public String getChatGptResponse(String prompt) throws Exception { String url
 * = "https://api.openai.com/v1/chat/completions";
 * 
 * HttpHeaders headers = new HttpHeaders();
 * headers.setContentType(MediaType.APPLICATION_JSON);
 * headers.setBearerAuth(apiKey);
 * 
 * ObjectNode message = objectMapper.createObjectNode(); message.put("role",
 * "user"); message.put("content", prompt);
 * 
 * ObjectNode requestBody = objectMapper.createObjectNode();
 * requestBody.put("model", model); requestBody.set("messages",
 * objectMapper.createArrayNode().add(message)); requestBody.put("max_tokens",
 * 1024);
 * 
 * HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(),
 * headers);
 * 
 * ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST,
 * entity, String.class);
 * 
 * ObjectNode jsonResponse = objectMapper.readValue(response.getBody(),
 * ObjectNode.class); return
 * jsonResponse.get("choices").get(0).get("message").get("content").asText().
 * trim(); } }
 */
