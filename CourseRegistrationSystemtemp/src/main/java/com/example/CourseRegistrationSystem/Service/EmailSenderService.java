@Service
public class BrevoEmailService {

    private static final String API_URL = "https://api.brevo.com/v3/smtp/email";

    @Async
    public void sendCourseRegistrationEmail(
            String toEmail,
            String studentName,
            String courseName) {

        try {
            String apiKey = System.getenv("BREVO_API_KEY");

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("api-key", apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            String body = """
            {
              "sender": { "email": "yourgmail@gmail.com", "name": "Course Registration System" },
              "to": [ { "email": "%s" } ],
              "subject": "🎓 Course Registration Confirmation - %s",
              "htmlContent": "<h2>Hello %s</h2><p>You are registered for <b>%s</b></p>"
            }
            """.formatted(toEmail, courseName, studentName, courseName);

            HttpEntity<String> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    API_URL, entity, String.class);

            System.out.println("Brevo Email Status: " + response.getStatusCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
