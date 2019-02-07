package br.com.leonardosugahara.security.springsecurityoauth2client.document;

public class Test {

    private long id;
    private String str;

    public Test() {
        super();
    }

    public Test(final long id, final String str) {
        super();

        this.id = id;
        this.str = str;
    }

    //

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
