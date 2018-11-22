enum ServiceType{
    REQUEST("REQUEST"),
    QUERY("QUERY"),
    RESPONSE("RESPONSE"),
    REGISTER("REGISTER"),
    REGISTER_SUPER("REGISTER_SUPER"),
    NA("N-A"),
    END("END"),
    FILE_SEPARATOR("#&"),
    META("META"),
    RESOURCE("RESOURCE");

    private String value;
    private ServiceType(String content) {
        this.value = content;
    }
    public String getValue(){
        return this.value;
    }
}