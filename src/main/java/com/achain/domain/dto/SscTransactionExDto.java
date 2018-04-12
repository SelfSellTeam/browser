package com.achain.domain.dto;

public class SscTransactionExDto {

    private Long id;

    private String to_addr;

    private String to_acct;

    private String amount;

    private String fee;

    private SscTransactionExDto(Builder builder) {
        id = builder.id;
        to_addr = builder.to_addr;
        to_acct = builder.to_acct;
        amount = builder.amount;
        fee = builder.fee;
    }

    public static final class Builder {
        private Long id;
        private String to_addr;
        private String to_acct;
        private String amount;
        private String fee;

        public Builder() {
        }

        public static Builder anActTransactionExDto(){
            return new Builder();
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder to_addr(String val) {
            to_addr = val;
            return this;
        }

        public Builder to_acct(String val) {
            to_acct = val;
            return this;
        }

        public Builder amount(String val) {
            amount = val;
            return this;
        }

        public Builder fee(String val) {
            fee = val;
            return this;
        }

        public SscTransactionExDto build() {
            return new SscTransactionExDto(this);
        }
    }
}
