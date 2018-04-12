package com.achain.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SscBlockDto implements Serializable {

    private Long id;

    private String blockId;

    private Long block_num;

    private String block_time;

    private Integer trans_num;

    private String trans_amount;

    private String signee;

    private Long block_size;

    private String block_elapsed_time;

    public static final class Builder {
        private Long id;
        private String blockId;
        private Long block_num;
        private String block_time;
        private Integer trans_num;
        private String trans_amount;
        private String signee;
        private Long block_size;
        private String block_elapsed_time;

        private Builder() {
        }

        public static Builder anActBlockDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder blockId(String blockId) {
            this.blockId = blockId;
            return this;
        }

        public Builder block_num(Long block_num) {
            this.block_num = block_num;
            return this;
        }

        public Builder block_time(String block_time) {
            this.block_time = block_time;
            return this;
        }

        public Builder trans_num(Integer trans_num) {
            this.trans_num = trans_num;
            return this;
        }

        public Builder trans_amount(String trans_amount) {
            this.trans_amount = trans_amount;
            return this;
        }

        public Builder signee(String signee) {
            this.signee = signee;
            return this;
        }

        public Builder block_size(Long block_size) {
            this.block_size = block_size;
            return this;
        }

        public Builder blockElapsedTime(String block_elapsed_time) {
            this.block_elapsed_time = block_elapsed_time;
            return this;
        }

        public SscBlockDto build() {
            SscBlockDto sscBlockDto = new SscBlockDto();
            sscBlockDto.setId(id);
            sscBlockDto.setBlockId(blockId);
            sscBlockDto.setBlock_num(block_num);
            sscBlockDto.setBlock_time(block_time);
            sscBlockDto.setTrans_num(trans_num);
            sscBlockDto.setTrans_amount(trans_amount);
            sscBlockDto.setSignee(signee);
            sscBlockDto.setBlock_size(block_size);
            sscBlockDto.setBlock_elapsed_time(block_elapsed_time);
            return sscBlockDto;
        }
    }
}
