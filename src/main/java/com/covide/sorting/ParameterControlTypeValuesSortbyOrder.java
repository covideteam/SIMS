package com.covide.sorting;

import java.util.Comparator;

import com.covideinfo.model.ParameterControlTypeValues;

public class ParameterControlTypeValuesSortbyOrder  implements Comparator<ParameterControlTypeValues>{
	// Used for sorting in ascending order of
    // roll number
    public int compare(ParameterControlTypeValues a, ParameterControlTypeValues b)
    {
        return a.getGlobalValues().getOrderNo() - b.getGlobalValues().getOrderNo();
    }
}
