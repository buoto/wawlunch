package kantoniak.com.wawlunch;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import kantoniak.com.wawlunch.data.Menu;

public class MenuAdapter extends FragmentStatePagerAdapter {

    private List<MenuFragment> menuFragments;

    public MenuAdapter(FragmentManager fm, List<Menu> menus) {
        super(fm);
        menuFragments = menus.stream().map(m -> MenuFragment.newInstance(m)).collect(Collectors.toList());
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
