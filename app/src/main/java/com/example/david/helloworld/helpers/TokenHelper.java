package com.example.david.helloworld.helpers;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.david.helloworld.models.user.TokenModel;
import com.example.david.helloworld.models.user.UserImage;

/**
 * Created by david on 7.2.2018..
 */

public class TokenHelper {

    public static TokenModel DecodeToken(String authToken) {
        TokenModel tokenModel = new TokenModel();
        if (!authToken.isEmpty()) {
            try {

                JWT jwt = new JWT(authToken);

                Claim idClaim = jwt.getClaim("Id");
                Claim firstNameClaim = jwt.getClaim("FirstName");
                Claim lastNameClaim = jwt.getClaim("LastName");
                Claim usernameClaim = jwt.getClaim("Username");
                Claim emailClaim = jwt.getClaim("Email");
                Claim adminClaim = jwt.getClaim("Admin");
                Claim imageClaim = jwt.getClaim("Image");
                Claim expirationDateClaim = jwt.getClaim("ExpirationDate");

                tokenModel.setId(idClaim.asInt());
                tokenModel.setFirstName(firstNameClaim.asString());
                tokenModel.setLastName(lastNameClaim.asString());
                tokenModel.setUsername(usernameClaim.asString());
                tokenModel.setEmail(emailClaim.asString());
                tokenModel.setAdmin(adminClaim.asBoolean());
                tokenModel.setImage(UserImage.values()[imageClaim.asInt()]);

                return tokenModel;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

}
