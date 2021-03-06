package com.khoa.myptit.libmoduleExpandable.Adapter;



import com.khoa.myptit.libmoduleExpandable.Model.ParentListItem;
import com.khoa.myptit.libmoduleExpandable.Model.ParentWrapper;

import java.util.ArrayList;
import java.util.List;

/*
 * Created at 10/26/19 11:03 PM by Khoa
 */

/**
 * Helper for {@link ExpandableRecyclerAdapter}.
 *
 * Created by Ryan Brooks on 6/11/15.
 */
public class ExpandableRecyclerAdapterHelper {

    /**
     * children, in order.
     *
     * @param parentItemList A list of the {@code ParentListItem} objects from
     *                       the {@link ExpandableRecyclerAdapter}
     * @return A list of all {@code ParentListItem} objects and their children, expanded
     */
    public static List<Object> generateParentChildItemList(List<? extends ParentListItem> parentItemList) {
        List<Object> parentWrapperList = new ArrayList<>();
        ParentListItem parentListItem;
        ParentWrapper parentWrapper;

        int parentListItemCount = parentItemList.size();
        for (int i = 0; i < parentListItemCount; i++) {
            parentListItem = parentItemList.get(i);
            parentWrapper = new ParentWrapper(parentListItem);
            parentWrapperList.add(parentWrapper);

            if (parentWrapper.isInitiallyExpanded()) {
                parentWrapper.setExpanded(true);

                int childListItemCount = parentWrapper.getChildItemList().size();
                for (int j = 0; j < childListItemCount; j++) {
                    parentWrapperList.add(parentWrapper.getChildItemList().get(j));
                }
            }
        }

        return parentWrapperList;
    }
}
