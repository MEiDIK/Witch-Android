package se.snylt.witch.viewbinder;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import android.view.View;

import java.util.HashMap;

import se.snylt.witch.viewbinder.viewfinder.ViewFinder;

import static junit.framework.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class ViewTargetViewBinderTest {

    private final static int VIEW_ID = 666;

    private final static String KEY = "valueKey";

    @Mock
    View rootView;

    @Mock
    View bindView;

    TestViewBinder viewBinder;

    TestViewHolder viewHolder;

    TestViewFinder viewFinder;

    Object target;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        target = new Object();

        viewFinder = spy(new TestViewFinder(rootView));
        viewFinder.childView(VIEW_ID, bindView);
        viewHolder = spy(new TestViewHolder());
        viewBinder = spy(new TestViewBinder(VIEW_ID, KEY));

        setIsNewValue(true);

        fail();
    }

    @Test
    public void bind_Should_InOrder_GetValueFromTarget_FindViewInViewFinder_SetViewInViewHolder_RunBindActionsAndModsWithValueAndView(){
        InOrder inOrder = Mockito.inOrder(viewBinder, viewFinder);

        viewBinder.setValue("123");

        // When
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        inOrder.verify(viewBinder).getValue(same(target));
        inOrder.verify(viewFinder).findViewById(eq(VIEW_ID));
        inOrder.verify(viewBinder).setView(same(viewHolder), same(bindView));
        // inOrder.verify(runner).bind(same(bindActionsAndMods), same(bindView), eq("123"));

        fail();
    }

    @Test
    public void bind_When_NewValue_Should_RunBindActionsAndModsWithValueAndView() {
        viewBinder.setValue("123");

        // When
        setIsNewValue(true);
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        // verify(runner).bind(same(bindActionsAndMods), same(bindView), eq("123"));
        fail();
    }

    @Test
    public void bind_When_IsNotNewValue_Should_Never_RunBindActionsAndModsWithValueAndView() {
        viewBinder.setValue("123");

        // When
        setIsNewValue(false);
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        // verify(runner, never()).bind(same(bindActionsAndMods), same(bindView), eq("123"));
        fail();
    }

    @Test
    public void bind_When_IsNotNewValue_IsAlwaysBind_Should_RunBindActionsAndModsWithValueAndView() {
        viewBinder.setValue("123");

        // When
        setIsNewValue(false);
        isAlwaysBind(true);
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        // verify(runner).bind(same(bindActionsAndMods), same(bindView), eq("123"));
        fail();
    }

    private void isAlwaysBind(boolean alwaysBind) {
        viewBinder.setAlwaysBind(alwaysBind);
    }

    @Test
    public void bind_When_TwoDifferentValues_Should_TwoTimes_RunBindActionsAndModsWithValueAndView(){
        // When
        viewBinder.setValue("123");
        viewBinder.bind(viewHolder, viewFinder, target);
        viewBinder.setValue("456");
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        // verify(runner, times(1)).bind(same(bindActionsAndMods), same(bindView), eq("123"));
        // verify(runner, times(1)).bind(same(bindActionsAndMods), same(bindView), eq("456"));
        fail();
    }


    @Test
    public void bind_With_Mods_Should_AddModsToBindActions(){
        viewBinder.setValue("123");

        // When
        Object[] mods = new Object[] {new Object(), new Object()};
        // viewBinder.bind(viewHolder, viewFinder, target, mods);

        // Then
        // verify(bindActions).applyMods(same(viewBinder), same(mods));
        fail();
    }

    @Test
    public void bind_With_ViewSetInViewHolder_Should_Not_FindView_Or_SetViewInViewHolder(){
        viewBinder.setValue("123");

        // When
        View view = mock(View.class);
        viewHolder.setView(view);
        Mockito.reset(viewHolder);
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        verify(viewHolder, never()).setView(any(View.class));
        verify(viewFinder, never()).findViewById(anyInt());
    }


    public void setIsNewValue(boolean isNewValue) {
        // when(runner.isNewValue(any(Object.class), any(Object.class))).thenReturn(isNewValue);
        fail();
    }

    private class TestViewHolder {

        private Object view;

        public Object getView() {
            return view;
        }

        public void setView(Object view) {
            this.view = view;
        }
    }


    private class TestViewBinder extends ViewBinder {

        private Object value;

        private boolean alwaysBind;

        public TestViewBinder(int viewId, String key) {
            super(viewId, key, null);
            fail();
        }

        @Override
        public Object getValue(Object target) {
            return this.value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        @Override
        public void setView(Object viewHolder, Object view) {
            ((TestViewHolder)viewHolder).setView(view);
        }

        @Override
        public Object getView(Object viewHolder) {
            return ((TestViewHolder)viewHolder).getView();
        }

        @Override
        public boolean isAlwaysBind() {
            return alwaysBind;
        }


        public void setAlwaysBind(boolean alwaysBind) {
            this.alwaysBind = alwaysBind;
        }
    }

    private class TestViewFinder implements ViewFinder {

        private final View rootView;

        private HashMap<Integer, View> views = new HashMap<>();

        private TestViewFinder(View rootView) {
            this.rootView = rootView;
        }

        @Override
        public View getRoot() {
            return rootView;
        }

        @Override
        public int getTag() {
            return 0;
        }

        @Override
        public View findViewById(int id) {
            return views.get(id);
        }

        @Override
        public Object getViewHolder(Object key) {
            return null;
        }

        @Override
        public void putViewHolder(Object key, Object viewHolder) {

        }

        @Override
        public TargetViewBinder getBinder(Object key) {
            return null;
        }

        @Override
        public void putBinder(Object key, TargetViewBinder targetViewBinder) {

        }

        void childView(int viewId, View view) {
            views.put(viewId, view);
        }
    }
}