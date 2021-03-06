package com.github.aisbergg.keycloak.authentication;
//package org.boon.utils;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.events.Errors;
import org.keycloak.models.ClientModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserModel;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Iterator;

public class RequireAttributeAuthenticator implements Authenticator {
       public  static  int status;

    private static HttpURLConnection connection;
    public static void main(String[] args) throws IOException {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
String url = "https://www.google.com";
URL request_url = new URL(url);
HttpURLConnection http_conn = (HttpURLConnection)request_url.openConnection();

System.out.println((http_conn.getResponseCode()));
            //Request Setup
            connection.setRequestMethod("GET");
            
        status = http_conn.getResponseCode();

        }catch (IOException e) {
            e.printStackTrace();
        }

    }


    protected static final Logger LOG = Logger.getLogger(RequireAttributeAuthenticator.class);

    public static final String CLIENT_ID_PLACEHOLDER = "{clientId}";

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        UserModel user = context.getUser();
        ClientModel client = context.getAuthenticationSession().getClient();
        Map<String, String> config = context.getAuthenticatorConfig().getConfig();

        String requiredAttrName = config.get(RequireAttributeAuthenticatorFactory.ATTRIBUTE_NAME).replace(CLIENT_ID_PLACEHOLDER, client.getClientId());
        String requiredAttrValue = config.get(RequireAttributeAuthenticatorFactory.ATTRIBUTE_VALUE);
        boolean sourceUser = Boolean.parseBoolean(config.get(RequireAttributeAuthenticatorFactory.SOURCE_USER));
        boolean sourceRole = Boolean.parseBoolean(config.get(RequireAttributeAuthenticatorFactory.SOURCE_ROLE));
        boolean sourceGroup = Boolean.parseBoolean(config.get(RequireAttributeAuthenticatorFactory.SOURCE_GROUP));

        String attrValue = getAttribute(requiredAttrName, sourceUser, sourceRole, sourceGroup, user);
         //status = 200;
        
       // if (attrValue != null && attrValue.equals(requiredAttrValue)) {
           if(status == 200){
            // successfully authenticated
            context.success();
            return;
        }

        // authentication failed
        LOG.debugf("Denied access for user '%s' to client '%s': User is missing the attribute '%s'", user.getUsername(), client.getName(), requiredAttrName);
        Response errorForm = context.form()
                .setError("Access Denied")
                .createErrorPage(Response.Status.FORBIDDEN);
        context.getEvent().user(user);
        context.getEvent().error(Errors.NOT_ALLOWED);
        context.failure(AuthenticationFlowError.INVALID_USER, errorForm);
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {}

    @Override
    public void action(AuthenticationFlowContext context) {}

    @Override
    public void close() {}

    private String getAttribute(String attrName, boolean sourceUser, boolean sourceRole, boolean sourceGroup, UserModel user) {
        // if (sourceUser) {
        //     // check if the user has the attribute
        //     String attrValue = user.getFirstAttribute(attrName);
        //     if (attrValue != null) return attrValue;
        // }

        // if (sourceRole) {
        //     // check if one of the groups, that the user is in, has the attribute
        //     for (Iterator<RoleModel> roleIter = user.getRoleMappingsStream().iterator(); roleIter.hasNext();) {
        //         String attrValue = roleIter.next().getFirstAttribute(attrName);
        //         if (attrValue != null) return attrValue;
        //     }
        // }
        
        // if (sourceGroup) {
        //     // check if one of the groups, that the user is in, has the attribute
        //     for (Iterator<GroupModel> groupIter = user.getGroupsStream().iterator(); groupIter.hasNext();) {
        //         String attrValue = groupIter.next().getFirstAttribute(attrName);
        //         if (attrValue != null) return attrValue;
        //     }
        // }
        
         return null;
    }
}
