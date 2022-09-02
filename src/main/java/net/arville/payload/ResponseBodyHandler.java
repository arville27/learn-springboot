package net.arville.payload;

public class ResponseBodyHandler {

    private ErrorSchema errorSchema;
    private OutputSchema outputSchema;


    public OutputSchema getOutputSchema() {
        return outputSchema;
    }

    public void setOutputSchema(OutputSchema outputSchema) {
        this.outputSchema = outputSchema;
    }

    public ErrorSchema getErrorSchema() {
        return errorSchema;
    }

    public void setErrorSchema(ErrorSchema errorSchema) {
        this.errorSchema = errorSchema;
    }

    public ResponseBodyHandler(OutputSchema outputSchema, ErrorSchema errorSchema) {
        this.outputSchema = outputSchema;
        this.errorSchema = errorSchema;
    }
}
