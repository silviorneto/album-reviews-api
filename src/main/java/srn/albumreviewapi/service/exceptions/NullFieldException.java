package srn.albumreviewapi.service.exceptions;

public class NullFieldException extends BusinessException{

    public NullFieldException(String resourceName) {
        super(resourceName + "cannot be null");
    }
}
