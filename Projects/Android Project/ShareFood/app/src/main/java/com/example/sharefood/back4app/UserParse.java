package com.example.sharefood.back4app;

import android.content.Context;
import android.widget.Toast;

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

    public String createUser(User user){
        final ParseObject entity = new ParseObject("Usuario");
        final String[] entityId = {null};
        entity.put("nome", user.getNome());
        entity.put("email", user.getEmail());
        entity.put("celular", user.getCelular());
        entity.put("senha", user.getSenha());
        entity.put("midiaFk", "pensarnissodepois");

        try{
            // Salva o novo usuário
            // O SaveCallback é total opcional
            entity.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    // Aqui posso checar se deu erro, se tiver. Senão, "e" deve ser nulo
                    System.out.println("Usuário salvo");
                    if(e == null){
                        entityId[0] = entity.getObjectId();
                        System.out.println(entityId[0]);
                    }else{
                        ParseUser.logOut();
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });
        }finally {
            return entityId[0];
        }
    }
}
