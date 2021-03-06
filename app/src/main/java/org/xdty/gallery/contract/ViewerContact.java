package org.xdty.gallery.contract;

import org.xdty.gallery.model.Media;

import java.util.List;

public interface ViewerContact {
    interface View extends BaseView<Presenter> {

        void updateOrientation(int width, int height);

        void hideSystemUIDelayed(int timeout);

        void cancelHideSystemUIDelayed();

        boolean isSystemUIVisible();

        void showSystemUI(boolean autoHide);

        void hideSystemUI();

        void replaceData(List<Media> medias, int position);

        void load(Media media);

        void setTitle(String name);

        void startTransition();
    }

    interface Presenter extends BasePresenter {

        void loadData(String uri, String parent, String host, int position);

        void pageSelected(int position);

        int getSelectedPosition();

        int getPosition();

        void clear();

        String getCurrentName();
    }
}
