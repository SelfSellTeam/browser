package com.achain.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SscTransactionForWalletDto implements Serializable{

    private Long id;

    private String trx_id;

    private String amount;

    private String trx_type;

    private String coinType;

    private String trade_Describe;

    private String from_addr;

    private Long block_num;

    private String from_acct;

    private String to_addr;

    private String sub_addr;

    private String to_acct;

    private String called_abi;

    private String trx_time;

    private String is_completed;

    private String contractId;

    /***/
    private String eventType;

    /***/
    private String eventParam;

    private String fee;

    private String memo;

    public static final class Builder {
        private Long id;
        private String trx_id;
        private String amount;
        private String trx_type;
        private String coinType;
        private String trade_Describe;
        private String from_addr;
        private Long block_num;
        private String from_acct;
        private String to_addr;
        private String sub_addr;
        private String to_acct;
        private String called_abi;
        private String trx_time;
        private String is_completed;
        private String contractId;
        private String eventType;
        private String eventParam;
        private String fee;
        private String memo;

        private Builder() {
        }

        public static Builder anActTransactionForWalletDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder trx_id(String trx_id) {
            this.trx_id = trx_id;
            return this;
        }

        public Builder amount(String amount) {
            this.amount = amount;
            return this;
        }

        public Builder trx_type(String trx_type) {
            this.trx_type = trx_type;
            return this;
        }

        public Builder coinType(String coinType) {
            this.coinType = coinType;
            return this;
        }

        public Builder trade_Describe(String trade_Describe) {
            this.trade_Describe = trade_Describe;
            return this;
        }

        public Builder from_addr(String from_addr) {
            this.from_addr = from_addr;
            return this;
        }

        public Builder block_num(Long block_num) {
            this.block_num = block_num;
            return this;
        }

        public Builder from_acct(String from_acct) {
            this.from_acct = from_acct;
            return this;
        }

        public Builder to_addr(String to_addr) {
            this.to_addr = to_addr;
            return this;
        }

        public Builder sub_addr(String sub_addr) {
            this.sub_addr = sub_addr;
            return this;
        }

        public Builder to_acct(String to_acct) {
            this.to_acct = to_acct;
            return this;
        }

        public Builder called_abi(String called_abi) {
            this.called_abi = called_abi;
            return this;
        }

        public Builder trx_time(String trx_time) {
            this.trx_time = trx_time;
            return this;
        }

        public Builder is_completed(String is_completed) {
            this.is_completed = is_completed;
            return this;
        }

        public Builder contractId(String contractId) {
            this.contractId = contractId;
            return this;
        }

        public Builder eventType(String eventType) {
            this.eventType = eventType;
            return this;
        }

        public Builder eventParam(String eventParam) {
            this.eventParam = eventParam;
            return this;
        }

        public Builder fee(String fee) {
            this.fee = fee;
            return this;
        }

        public Builder memo(String memo) {
            this.memo = memo;
            return this;
        }

        public SscTransactionForWalletDto build() {
            SscTransactionForWalletDto sscTransactionForWalletDto = new SscTransactionForWalletDto();
            sscTransactionForWalletDto.setId(id);
            sscTransactionForWalletDto.setTrx_id(trx_id);
            sscTransactionForWalletDto.setAmount(amount);
            sscTransactionForWalletDto.setTrx_type(trx_type);
            sscTransactionForWalletDto.setCoinType(coinType);
            sscTransactionForWalletDto.setTrade_Describe(trade_Describe);
            sscTransactionForWalletDto.setFrom_addr(from_addr);
            sscTransactionForWalletDto.setBlock_num(block_num);
            sscTransactionForWalletDto.setFrom_acct(from_acct);
            sscTransactionForWalletDto.setTo_addr(to_addr);
            sscTransactionForWalletDto.setSub_addr(sub_addr);
            sscTransactionForWalletDto.setTo_acct(to_acct);
            sscTransactionForWalletDto.setCalled_abi(called_abi);
            sscTransactionForWalletDto.setTrx_time(trx_time);
            sscTransactionForWalletDto.setIs_completed(is_completed);
            sscTransactionForWalletDto.setContractId(contractId);
            sscTransactionForWalletDto.setEventType(eventType);
            sscTransactionForWalletDto.setEventParam(eventParam);
            sscTransactionForWalletDto.setFee(fee);
            sscTransactionForWalletDto.setMemo(memo);
            return sscTransactionForWalletDto;
        }
    }
}
