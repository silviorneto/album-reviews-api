package srn.albumreviewapi.service.exceptions;

public class NotFoundException extends BusinessException{

    public NotFoundException() {
        super("Resource not found");
    }
}
