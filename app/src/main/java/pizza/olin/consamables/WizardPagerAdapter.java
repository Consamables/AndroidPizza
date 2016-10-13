package pizza.olin.consamables;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class WizardPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> wizardSteps;

    public WizardPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> wizardSteps) {
        super(fragmentManager);

        this.wizardSteps = wizardSteps;
    }

    @Override
    public int getCount() {
        return wizardSteps.size();
    }

    @Override
    public Fragment getItem(int position) {
        return wizardSteps.get(position);
    }
}
