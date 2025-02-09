package ChainAbuse;

public class BitcoinAddressFactory {
    public static BitcoinAddress createBitcoinAddress(String address) {
        return new BitcoinAddress(address);
    }
}
