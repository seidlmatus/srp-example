package com.example.srpexample.contoller;

import com.example.srpexample.models.CAuth;
import com.example.srpexample.jwt.CLoginResponse;
import com.example.srpexample.jwt.Clogin;
import com.example.srpexample.models.CReg;
import com.example.srpexample.tmp.TmpStorage;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.agreement.srp.SRP6Server;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.SecureRandom;

@RestController
@RequestMapping("/")
public class TemplateController {

    private static final BigInteger g_1024 = BigInteger.valueOf(2);
    private static final BigInteger N_1024 = fromHex("EEAF0AB9ADB38DD69C33F80AFA8FC5E86072618775FF3C0B9EA2314C"
            + "9C256576D674DF7496EA81D3383B4813D692C6E0E0D5D8E250B98BE4"
            + "8E495C1D6089DAD15DC7D7B46154D6B6CE8EF4AD69B15D4982559B29"
            + "7BCF1885C529F566660E57EC68EDBC3C05726CC02FD4CBF4976EAA9A"
            + "FD5138FE8376435B9FC61D2FC0EB06E3");
    private static TmpStorage storage = new TmpStorage();
    private final SecureRandom random = new SecureRandom();

    private static  SRP6Server server =new SRP6Server();

    private static BigInteger fromHex(String hex) {
        return new BigInteger(1, Hex.decode(hex));
    }


    @PostMapping("reg")
    public String reg(@RequestBody CReg request) {
        storage.save(request);

        return "registration success";
    }


    @PostMapping("login")
    public CLoginResponse login(@RequestBody Clogin clogin) {
        CReg reg = storage.findByNick(clogin.getUsername());

        server.init(N_1024, g_1024, fromHex(reg.getVerifier()), new SHA256Digest(), random);
        BigInteger B = server.generateServerCredentials();
        return new CLoginResponse(reg.getSalt(), B.toString());
    }

    @PostMapping("auth")
    public String auth(@RequestBody CAuth cAuth) throws CryptoException {
        if (fromHex(cAuth.getK()) == server.calculateSecret(fromHex(cAuth.getA()))){
            return  "TRUE";
        }else {
            return "FALSE";
        }

    }

    @GetMapping("srp")
    public String getCourses() {
        return "SRP is Working";
    }
}
