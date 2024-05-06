package com.pepop99.emaildispatcher.metadata;

import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class GrantMeta {
    final private GrantType grantType;
    final private ArrayList<String> tags;
    final private String nonProfitLegalName;
    final private String grantSubmissionName;
    final private String Stage;
    final private String foundationOwner;
    final private int requestedAmount;
    final private int rewardedAmount;
    final private String durationStart;
    final private String durationEnd;
    private final String additionalFileFolderPath;
    private final String grandSubmissionId;
}

enum GrantType {
    OPERATING_GRANT,
    PROJECT_GRANT
}
