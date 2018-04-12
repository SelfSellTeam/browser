package com.achain.domain.dto;

import lombok.Data;

@Data
public class SscContractInfoDto {
    private Integer id;

    private String contract_id;

    private String name;

    private Integer status;

    private String owner_address;

    private String owner_name;

    private Long balance;

    private SscContractInfoDto(Builder builder) {
        setId(builder.id);
        setContract_id(builder.contract_id);
        setName(builder.name);
        setStatus(builder.status);
        setOwner_address(builder.owner_address);
        setOwner_name(builder.owner_name);
        setBalance(builder.balance);
    }

    public static final class Builder {
        private Integer id;
        private String contract_id;
        private String name;
        private Integer status;
        private String owner_address;
        private String owner_name;
        private Long balance;

        public Builder() {
        }

        public static Builder anActContractInfoDto() {
            return new Builder();
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder contract_id(String val) {
            contract_id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder status(Integer val) {
            status = val;
            return this;
        }

        public Builder owner_address(String val) {
            owner_address = val;
            return this;
        }

        public Builder owner_name(String val) {
            owner_name = val;
            return this;
        }

        public Builder balance(Long val) {
            balance = val;
            return this;
        }

        public SscContractInfoDto build() {
            return new SscContractInfoDto(this);
        }
    }
}
