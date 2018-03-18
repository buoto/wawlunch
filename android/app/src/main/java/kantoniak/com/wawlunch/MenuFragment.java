package kantoniak.com.wawlunch;

import android.content.Context;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import kantoniak.com.wawlunch.data.Dish;
import kantoniak.com.wawlunch.data.Menu;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {
    private final Menu menu;

    private OnFragmentInteractionListener mListener;

    public MenuFragment(Menu menu) {
        this.menu = menu;
    }

    public static MenuFragment newInstance(Menu menu) {
        MenuFragment fragment = new MenuFragment(menu);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_menu, container, false);
        makeUI(result);
        return result;
    }

    private void makeUI(View view) {
        // Przepraszam za napisanie tego kodu...
        // Sorry for writing this code...

        LinearLayoutCompat first = view.findViewById(R.id.menu_first);
        LinearLayoutCompat main = view.findViewById(R.id.menu_main);
        LinearLayoutCompat dessert = view.findViewById(R.id.menu_dessert);
        LinearLayoutCompat drink = view.findViewById(R.id.menu_drink);

        RadioGroup firstRadios = (RadioGroup) first.getChildAt(1);
        RadioGroup mainRadios = (RadioGroup) main.getChildAt(1);
        RadioGroup dessertRadios = (RadioGroup) dessert.getChildAt(1);
        RadioGroup drinkRadios = (RadioGroup) drink.getChildAt(1);

        int firstCount = 0;
        int mainCount = 0;
        int dessertCount = 0;
        int drinkCount = 0;

        for (int i = 0; i < 3; i++) {
            firstRadios.getChildAt(i).setVisibility(View.GONE);
            mainRadios.getChildAt(i).setVisibility(View.GONE);
            dessertRadios.getChildAt(i).setVisibility(View.GONE);
            drinkRadios.getChildAt(i).setVisibility(View.GONE);
        }

        for (Dish d : menu.getDishes()) {
            RadioButton option = null;

            switch (d.getType()) {
                case Dish.Type.SOUP:
                    option = (RadioButton) firstRadios.getChildAt(firstCount);
                    firstCount++;
                    break;
                case Dish.Type.MAIN:
                    option = (RadioButton) mainRadios.getChildAt(mainCount);
                    mainCount++;
                    break;
                case Dish.Type.DESS:
                    option = (RadioButton) dessertRadios.getChildAt(dessertCount);
                    dessertCount++;
                    break;
                case Dish.Type.DRIN:
                    option = (RadioButton) drinkRadios.getChildAt(drinkCount);
                    drinkCount++;
                    break;
            }

            option.setText(d.getName());
            option.setVisibility(View.VISIBLE);
        }

        first.setVisibility(firstCount > 0 ? View.VISIBLE : View.GONE);
        main.setVisibility(mainCount > 0 ? View.VISIBLE : View.GONE);
        dessert.setVisibility(dessertCount > 0 ? View.VISIBLE : View.GONE);
        drink.setVisibility(drinkCount > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
