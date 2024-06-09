package lol.pots.core.security;


public class License {

    private static final String TAG = "LicenseCheck"; // Tag for debug messages

    /*
    OkHttpClient client = new OkHttpClient();

    public boolean checkLicense() {

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.wove.host/checkLicensePostLoad").newBuilder();
        urlBuilder.addQueryParameter("product", "Arctic");
        urlBuilder.addQueryParameter("license", retrieveLicenseKey());

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (response.code() == 200) {
                return true;
            } else {

                System.out.println("-XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX-");
                System.out.println(response);
                System.exit(0);
                return false;
            }
        } catch (Exception e) {
            System.exit(0);

            return false;
        }
    }




    public String retrieveLicenseKey() {

        try {
            Plugin endyLoader = Bukkit.getPluginManager().getPlugin("Cosmo");

            if (endyLoader != null) {
                Method getLicenseKeyMethod = endyLoader.getClass().getMethod("AmericanStandardShitter");

                String licenseKey = (String) getLicenseKeyMethod.invoke(endyLoader);

                return licenseKey;
            } else {
                System.exit(69);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("-IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII-");

            System.exit(69);
        }

        return null;
    }
     */
}
