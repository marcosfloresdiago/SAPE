//package es.uji.ei1027.trabajoFinal.services;
//import java.security.Security;
//import java.util.*;
//import javax.mail.*;
//import javax.mail.internet.*;
//
//import com.sun.mail.smtp.SMTPTransport;
//
//import javax.activation.*;
//
//public class EnviarMails {
//
//	public void enviarMail(String emailDestinatario,String passwordDestinatario, String emailReceptor,String asunto,String cuerpoMensaje) throws Exception {     
//		
//		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
//        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
//
//        // Propiedades de la comunicacion
//        //Falta el tratado de puertos por si una empresa tiene un gmail,yahoo,etc
//        Properties props = System.getProperties();
//        props.setProperty("mail.smtps.host", "smtp.gmail.com");
//        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
//        props.setProperty("mail.smtp.socketFactory.fallback", "false");
//        props.setProperty("mail.smtp.port", "465");
//        props.setProperty("mail.smtp.socketFactory.port", "465");
//        props.setProperty("mail.smtps.auth", "true");
//
//        /*
//        Se establec en false para que asi se cierre la conexon automaticamente
//
//        */
//        props.put("mail.smtps.quitwait", "false");
//
//        Session session = Session.getInstance(props, null);
//
//        // -- Crear el mensaje 
//        final MimeMessage msg = new MimeMessage(session);
//
//        // -- Setear desde quien se envia  --
//        msg.setFrom(new InternetAddress( emailDestinatario));
//        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestinatario, false));
//        msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse("", false));
//      
//
//        //Contenido del mensaje
//        msg.setSubject(asunto);
//        msg.setText(cuerpoMensaje, "utf-8");
//        msg.setSentDate(new Date());
//
//        SMTPTransport t = (SMTPTransport)session.getTransport("smtps");
//
//        //Falta cambiar los puertos
//        t.connect("smtp.gmail.com", emailDestinatario, passwordDestinatario);
//        t.sendMessage(msg, msg.getAllRecipients());      
//        t.close();
//    }
//}