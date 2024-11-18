import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.io.IOException

@ControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    // README에서 설명.
    @ExceptionHandler(IOException::class)
    fun handleIOException(ex: IOException) {
        logger.debug("IOException 발생: ${ex.message}");
    }
}
