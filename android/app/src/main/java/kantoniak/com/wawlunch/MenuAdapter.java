package kantoniak.com.wawlunch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Arrays;
import java.util.List;

public class MenuAdapter extends FragmentStatePagerAdapter {

    private static final List<MenuFragment> menuFragments = Arrays.asList(
        MenuFragment.newInstance("A", "B"),
        MenuFragment.newInstance("A", "B"),
        MenuFragment.newInstance("A", "B")
    );

    public MenuAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return menuFragments.get(position);
    }

    @Override
    public int getCount() {
        return menuFragments.size();
    }
}
