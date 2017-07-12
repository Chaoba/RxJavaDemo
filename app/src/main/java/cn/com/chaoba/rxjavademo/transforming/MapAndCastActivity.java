package cn.com.chaoba.rxjavademo.transforming;

import android.os.Bundle;
import android.view.View;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class MapAndCastActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Map");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapObserver().subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log("Map:" + integer);
                    }
                });
            }
        });
        mRButton.setText("Cast");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                castObserver().subscribe(new Action1<Dog>() {
                    @Override
                    public void call(Dog dog) {
                        log("Cast:" + dog.getName());
                    }
                });
            }
        });
    }

    private Observable<Integer> mapObserver() {
        return Observable.just(1, 2, 3)
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return integer * 10;
                    }
                });
    }

    private Observable<Dog> castObserver() {
        return Observable.just(getAnimal())
                .cast(Dog.class);
    }

    Animal getAnimal() {
        return new Dog();
    }

    class Animal {
        protected String name = "Animal";

        Animal() {
            log("create " + name);
        }

        String getName() {
            return name;
        }
    }

    class Dog extends Animal {
        Dog() {
            name = getClass().getSimpleName();
            log("create " + name);
        }

    }
}
