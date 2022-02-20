package org.maria.enroll_me;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ClientRow {
    private int id;
    private String app_id;
    private String name;
    private String phone;
    private String socilaMedia;
    private String location;
}
