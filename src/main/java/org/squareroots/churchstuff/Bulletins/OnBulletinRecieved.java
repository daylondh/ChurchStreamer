package org.squareroots.churchstuff.Bulletins;

public interface OnBulletinRecieved {
    void doOnSuccess(String url);
    void doOnFailure();
}
