package com.uj.yuri.budgetflow.DataManagment;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;

import static java.math.RoundingMode.HALF_UP;

/**
 * Created by Yuri on 03.01.2017.
 */


public class Money  {

    /**
     * Why me
     */
    private static final int[] cents = new int[]{1, 10, 100, 1000};

    private BigDecimal amount;

    private Currency currency;

    private MathContext DEFAULT_CONTEXT = new MathContext( 10, RoundingMode.HALF_DOWN );

    public Money(long amount, Currency currency) {
        this.currency = currency;
        this.amount = BigDecimal.valueOf(amount, currency.getDefaultFractionDigits());
    }

    /**
     * @param amount in base monetary unit
     * @param currCode
     */
    public Money(long amount, String currCode)  {
        this( amount, Currency.getInstance(currCode) );
    }

    /**
     * @param amount
     * @param curr
     */
    public Money(double amount, Currency curr) {
        this.currency = curr;
        BigDecimal bd = BigDecimal.valueOf( amount );
        this.amount = bd.setScale(centFactor(), HALF_UP);
    }

    private Money() {
    }

    /**
     * @param amount
     * @param currCode
     */
    public Money(double amount, String currCode)  {
        this.currency = Currency.getInstance(currCode);
        BigDecimal bd = BigDecimal.valueOf( amount );
        this.amount = bd.setScale( currency.getDefaultFractionDigits(), HALF_UP);
    }

    /**
     * @param bigDecimal
     * @param currCode
     */
    public Money(BigDecimal bigDecimal, String currCode ) {
        this.currency = Currency.getInstance(currCode);
        this.amount = bigDecimal.setScale( currency.getDefaultFractionDigits(), HALF_UP);
    }

    /**
     * @param currency
     */
    public Money(BigDecimal bigDecimal, Currency currency) {
        this.currency = currency;
        this.amount = bigDecimal.setScale( currency.getDefaultFractionDigits(), HALF_UP);
    }


    public boolean assertSameCurrencyAs(Money money) {
        return this.currency != null && money != null;

    }

    private int centFactor() {
        return cents[ getCurrency().getDefaultFractionDigits() ];
    }

    public BigDecimal amount() {
        return amount;
    }


    public Currency getCurrency() {
        return currency;
    }

    public static Money dollars(double amount) {
        Money result = null;

        result = new Money(amount, "USD");

        return result;
    }

    public static Money dollars(long amount) {
        Money result = null;

        result = new Money(amount, "USD");

        return result;
    }

    public static Money pounds(double amount) {
        Money result = null;

            result = new Money(amount, "GBP");

        return result;
    }

    public static Money pounds(long amount) {
        Money result = null;

            result = new Money(amount, "GBP");

        return result;
    }

    public static Money pounds(BigDecimal amount) {
        Money result = null;

            result = new Money(amount, "GBP");

        return result;
    }

    @Override
    public int hashCode() {
        int hash = (int) ( amount.hashCode() ^ (amount.hashCode() >>> 32) );
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Money && equals((Money) other));
    }

    public boolean equals(Money other) {
        return ( currency.equals(other.currency) && (amount.equals(other.amount)) );
    }

    public Money add(Money other) throws Exception{
        assertSameCurrencyAs( other );
        return newMoney(amount.add(other.amount, DEFAULT_CONTEXT));
    }

    private int compareTo(Money money) throws Exception {
        assertSameCurrencyAs( money );
        return amount.compareTo( money.amount );
    }

    public Money multiply(BigDecimal amount) {
        return new Money( this.amount().multiply(amount, DEFAULT_CONTEXT), currency);
    }

    public Money multiply( BigDecimal amount, RoundingMode roundingMode ) {
        MathContext ct = new MathContext( currency.getDefaultFractionDigits(), roundingMode );
        return new Money( amount().multiply(amount, ct), currency);
    }

    private Money newMoney(BigDecimal amount) {
        return new Money( amount, this.currency );
    }

    public Money multiply(double amount) {
        return multiply( new BigDecimal( amount ) );
    }

    public Money subtract(Money other) throws Exception {
        assertSameCurrencyAs(other);
        return newMoney( amount.subtract(other.amount, DEFAULT_CONTEXT) );
    }

    public int compareTo(Object other) throws Exception {
        return compareTo((Money) other);
    }

    public boolean greaterThan(Money other)throws Exception {
        return (compareTo(other) > 0);
    }

    public Money divideByNumber( double divisor){
        BigDecimal div = BigDecimal.valueOf( divisor );
        BigDecimal ans = this.amount.divide(div, DEFAULT_CONTEXT);
        return new Money(ans, this.currency);
    }

    public int getQuotient( Money divisor ){
        BigDecimal ans = this.amount.divide(divisor.amount, RoundingMode.DOWN);
        return ans.intValue();
    }

    /**
     * divides toe moneys and return the quotient and Remainder this method has been customised,
     * for my money transfer needs...sorry
     * @param divisor
     * @return
     */
    public int[] getQuotientandRemainder(Money divisor){
        int[] ans = new int[2];
        BigDecimal[] bdArr = this.amount.divideAndRemainder(divisor.amount, DEFAULT_CONTEXT);
        BigDecimal quo = bdArr[0];
        BigDecimal rem = bdArr[1];
        ans[0] = quo.intValue();
        if( rem.compareTo(BigDecimal.ZERO) == 0 ){
            ans[1] =0;
        }else{
            ans[1] = 1;
        }
        return ans;
    }

    public String toFormattedString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        nf.setCurrency( currency );
        nf.setGroupingUsed( true );
        nf.setMaximumFractionDigits( currency.getDefaultFractionDigits() );
        return nf.format( this.amount.doubleValue() );
    }

    public String getCurrencyCode() {
        return currency.getCurrencyCode();
    }

    @Override
    public String toString() {
        return amount.toString();
    }


    public int signum() {
        return amount.signum();
    }
}