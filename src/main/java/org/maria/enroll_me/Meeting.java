package org.maria.enroll_me;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@ToString
public class Meeting {
    int id;
    String applicationId;
    int userId;
    String meetupDate;
    Date createDate;
}
