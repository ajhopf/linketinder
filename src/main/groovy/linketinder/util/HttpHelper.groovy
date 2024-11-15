package linketinder.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

import javax.servlet.http.HttpServletResponse

class HttpHelper {
    static void writeResponse(
            HttpServletResponse response,
            int status,
            Object object,
            String message = null
            ) {
        response.setContentType("application/json")
        response.setStatus(status)

        if (message) {
            response.getWriter().write("{\"message\": \"${message}\"}");
        } else {
            ObjectMapper mapper = new ObjectMapper()
            mapper.registerModule(new JavaTimeModule())
            String jsonResponse = mapper.writeValueAsString(object)
            response.getWriter().write(jsonResponse)
        }
    }
}
