package com.covide.sorting;

import java.util.Comparator;

import com.covideinfo.model.LanguageSpecificGroupDetails;

public class LanguageSpecificGroupDetailsSortByOrder  implements Comparator<LanguageSpecificGroupDetails> {
	// Used for sorting in ascending order of
    // roll number
    public int compare(LanguageSpecificGroupDetails a, LanguageSpecificGroupDetails b)
    {
        return a.getGloblgroupId().getOrderNo() - b.getGloblgroupId().getOrderNo();
    }
}
