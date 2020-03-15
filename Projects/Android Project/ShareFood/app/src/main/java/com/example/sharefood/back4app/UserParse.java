package com.example.sharefood.back4app;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.sharefood.Constants;
import com.example.sharefood.SessionManager;
import com.example.sharefood.activity.RegisterActivity;
import com.example.sharefood.entity.User;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class UserParse {

    private Context context;

    public UserParse(Context _context){
        this.context = _context;
    }

    public void createUser(User user){
        final ParseObject entity = new ParseObject("Usuario");
        entity.put("nome", user.getNome());
        entity.put("email", user.getEmail());
        entity.put("senha", user.getSenha());
        entity.put("tipo", user.getUserType());
        entity.put("midiaFk", "pensarnissodepois");

        try{
            // Salva o novo usuário
            // O SaveCallback é total opcional
            entity.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    // Aqui posso checar se deu erro, se tiver. Senão, "e" deve ser nulo
                    if(e == null){
                        String entityId = entity.getObjectId();
                        System.out.println("inside = "+entityId);

                        if(entityId != null) {
                            SessionManager sessionManager = new SessionManager(context);
                            sessionManager.createSession(entityId, entity.get("email").toString(), entity.get("nome").toString(), entity.get("tipo").toString());
                        }
                    }else{
                        ParseUser.logOut();
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });
        }finally {
            return ;
        }
    }
}
