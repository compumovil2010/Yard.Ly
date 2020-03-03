import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yardly.R;

public class OpinionesAdapter {
    private static final int CONTACT_ID_INDEX = 0;
    private static final int DISPLAY_NAME_INDEX = 1;
    public OpinionesAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    public View newView(Context context, ViewGroup parent) {
        return LayoutInflater.from(context)
                .inflate(R.layout.opiniones, parent, false);
    }
    public void bindView(View view, Context context) {
        TextView asunto = (TextView) view.findViewById(R.id.idContacto);
        TextView comentario = (TextView) view.findViewById(R.id.nombre);
        int idnum = cursor.getInt(CONTACT_ID_INDEX);
        String nombre = cursor.getString(DISPLAY_NAME_INDEX);
        tvIdContacto.setText(String.valueOf(idnum));
        tvNombre.setText(nombre);
    }
}
