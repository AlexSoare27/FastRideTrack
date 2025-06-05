package org.ispw.fastridetrack.Bean;

public class EmailBean {
    private String email;        // L'indirizzo email del destinatario
    private String subject;      // Oggetto dell'email
    private String body;         // Corpo dell'email
    private String rideDetails;  // Dettagli della corsa (in caso di email relative alle corse)

    // Costruttore
    public EmailBean(String email, String subject, String body, String rideDetails) {
        this.email = email;
        this.subject = subject;
        this.body = body;
        this.rideDetails = rideDetails;
    }

    // Getter e Setter per email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter e Setter per subject
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    // Getter e Setter per body
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    // Getter e Setter per rideDetails
    public String getRideDetails() {
        return rideDetails;
    }

    public void setRideDetails(String rideDetails) {
        this.rideDetails = rideDetails;
    }

    // Metodo toString per visualizzare facilmente le informazioni dell'email
    @Override
    public String toString() {
        return "EmailBean{" +
                "email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", rideDetails='" + rideDetails + '\'' +
                '}';
    }
}

