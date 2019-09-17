package com.jc.model;

import lombok.Data;

@Data
public class TysProduceBomDetail {
    private int id;
    private int product_id;
    private int number;
    private int product_bom_id;
    private String name;
    private String model_type;

}
