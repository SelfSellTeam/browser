package com.achain.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.security.Key;

/**
 * Created by qiangkz on 2017/8/3.
 */
@Data
public class WalletInfo implements Serializable {

  private String walletName;

  private String url;

  private String actRpcUser;

  private String offineWalletAmount;

  private String password;

  private Key key;

  public static final class Builder {
    private String walletName;
    private String url;
    private String actRpcUser;
    private String offineWalletAmount;
    private String password;
    private Key key;

    private Builder() {
    }

    public static Builder aWalletInfo() {
      return new Builder();
    }

    public Builder walletName(String walletName) {
      this.walletName = walletName;
      return this;
    }

    public Builder url(String url) {
      this.url = url;
      return this;
    }

    public Builder actRpcUser(String actRpcUser) {
      this.actRpcUser = actRpcUser;
      return this;
    }

    public Builder offineWalletAmount(String offineWalletAmount) {
      this.offineWalletAmount = offineWalletAmount;
      return this;
    }

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Builder key(Key key) {
      this.key = key;
      return this;
    }

    public WalletInfo build() {
      WalletInfo walletInfo = new WalletInfo();
      walletInfo.setWalletName(walletName);
      walletInfo.setUrl(url);
      walletInfo.setActRpcUser(actRpcUser);
      walletInfo.setOffineWalletAmount(offineWalletAmount);
      walletInfo.setPassword(password);
      walletInfo.setKey(key);
      return walletInfo;
    }
  }
}
