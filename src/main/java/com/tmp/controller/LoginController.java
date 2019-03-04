package com.tmp.controller;

import java.nio.charset.StandardCharsets;
import java.security.DigestException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tmp.entity.DashboardRequirement;
import com.tmp.entity.User;
import com.tmp.util.TMPUtil;

@Controller
public class LoginController {
	@Autowired(required = true)
	@Qualifier("tmpUtil")
	TMPUtil tmpUtil;

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login(ModelMap model) {
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("login", new User());
		mav.addObject("errorMsg", false);
		mav.addObject("message", false);
		return mav;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboardRequirement(HttpServletRequest request) {
		ModelAndView model = new ModelAndView("dashboard");
		model.addObject("description", "user page !");
		HttpSession session = request.getSession();
		String userId = session.getAttribute("user").toString();
		model.addObject("dashboardRequirementsJson", tmpUtil.getRequirementJson(userId));
		model.addObject("dashboardRequirement", new DashboardRequirement());
		model.addObject("dashboardblockJson", tmpUtil.getDashboardJson(userId));		
		if(session != null && !session.isNew()) {
		   //do something here
		} else {
		    model.setViewName("login");
		}

		model.addObject("accountValuesJson", tmpUtil.getAccountDetails(userId));
		model.addObject("projectValuesJson", tmpUtil.getProjectDetails(userId));
		model.addObject("locationJson", tmpUtil.getConfigKeyValues(2));
		model.addObject("primarySkillJson", tmpUtil.getConfigKeyValues(11));
		model.addObject("profileSourceJson",tmpUtil.getConfigKeyValues(7));
		model.addObject("initialEvaluationResultJson",tmpUtil.getConfigKeyValues(8));
		model.addObject("customerInterviewStatusJson",tmpUtil.getConfigKeyValues(9));
		model.addObject("profilesJson", tmpUtil.getProfiles());
		model.addObject("criticalJson",tmpUtil.getConfigKeyValues(1));
		model.addObject("intimationModeJson",tmpUtil.getConfigKeyValues(3));
		model.addObject("requirementTypeJson",tmpUtil.getConfigKeyValues(4));
		model.addObject("positionStatusJson",tmpUtil.getConfigKeyValues(5));
		model.addObject("opportunityStatusJson",tmpUtil.getConfigKeyValues(6));
		model.addObject("skillCategoryJson",tmpUtil.getConfigKeyValues(10));

		return model;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.POST)
	public ModelAndView processDashboardRequirement(HttpServletRequest request,
			@ModelAttribute("dashboardRequirement") DashboardRequirement dashboardRequirement) {
		ModelAndView model = new ModelAndView("dashboard");
		HttpSession session = request.getSession();
		String userId = session.getAttribute("user").toString();
		model.addObject("description", "user page !");
		model.addObject("dashboardRequirementsJson", tmpUtil.getRequirementJson(dashboardRequirement, userId));
		model.addObject("dashboardblockJson", tmpUtil.getDashboardJson(dashboardRequirement, userId));
		if(session != null && !session.isNew()) {
			   //do something here
			} else {
			    model.setViewName("login");
			}
		model.addObject("accountValuesJson", tmpUtil.getAccountDetails(userId));
		model.addObject("projectValuesJson", tmpUtil.getProjectDetails(userId));
		model.addObject("locationJson", tmpUtil.getConfigKeyValues(2));
		model.addObject("primarySkillJson", tmpUtil.getConfigKeyValues(11));
		model.addObject("profileSourceJson",tmpUtil.getConfigKeyValues(7));
		model.addObject("initialEvaluationResultJson",tmpUtil.getConfigKeyValues(8));
		model.addObject("customerInterviewStatusJson",tmpUtil.getConfigKeyValues(9));
		model.addObject("profilesJson", tmpUtil.getProfiles());
		model.addObject("criticalJson",tmpUtil.getConfigKeyValues(1));
		model.addObject("intimationModeJson",tmpUtil.getConfigKeyValues(3));
		model.addObject("requirementTypeJson",tmpUtil.getConfigKeyValues(4));
		model.addObject("positionStatusJson",tmpUtil.getConfigKeyValues(5));
		model.addObject("opportunityStatusJson",tmpUtil.getConfigKeyValues(6));
		model.addObject("skillCategoryJson",tmpUtil.getConfigKeyValues(10));
		return model;
	}
	
	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.POST)
	public ModelAndView performLogin(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute("login") @Valid User userlogin) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		ModelAndView mav = null;
		String username = userlogin.getName();
		String password = userlogin.getPassword();

		String secret = "TMP SECRET";
		String cipherText = password;

		byte[] cipherData = Base64.getDecoder().decode(cipherText);
		byte[] saltData = Arrays.copyOfRange(cipherData, 8, 16);

		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		final byte[][] keyAndIV = GenerateKeyAndIV(32, 16, 1, saltData, secret.getBytes(StandardCharsets.UTF_8), md5);
		SecretKeySpec key = new SecretKeySpec(keyAndIV[0], "AES");
		IvParameterSpec iv = new IvParameterSpec(keyAndIV[1]);

		byte[] encrypted = Arrays.copyOfRange(cipherData, 16, cipherData.length);
		Cipher aesCBC = Cipher.getInstance("AES/CBC/PKCS5Padding");
		aesCBC.init(Cipher.DECRYPT_MODE, key, iv);
		byte[] decryptedData = aesCBC.doFinal(encrypted);
		String decryptedText = new String(decryptedData, StandardCharsets.UTF_8);

		User dbUser = tmpUtil.getLoginDetails(username, decryptedText);
		
		if (dbUser.getId()>0) {//Valid user
			
			System.out.println("User Login Successful");
			
			HttpSession session = request.getSession(false);
			if(null == session || session.isNew()) {
				mav = new ModelAndView("login");
				return mav;
			}
			session.setAttribute("user", dbUser.getId());
			session.setAttribute("userName", dbUser.getName()); 
			session.setAttribute("displayName", dbUser.getDisplayName());
			session.setAttribute("userRole", dbUser.getRole().getDisplay());
			mav = new ModelAndView("dashboard");
			
			String userId = session.getAttribute("user").toString();
			mav.addObject("dashboardRequirementsJson", tmpUtil.getRequirementJson(userId));
			mav.addObject("dashboardRequirement", new DashboardRequirement());
			mav.addObject("dashboardblockJson", tmpUtil.getDashboardJson(userId));
			
			mav.addObject("accountValuesJson", tmpUtil.getAccountDetails(userId));
			mav.addObject("projectValuesJson", tmpUtil.getProjectDetails(userId));
			mav.addObject("locationJson", tmpUtil.getConfigKeyValues(2));
			mav.addObject("primarySkillJson", tmpUtil.getConfigKeyValues(11));
			mav.addObject("profileSourceJson",tmpUtil.getConfigKeyValues(7));
			mav.addObject("initialEvaluationResultJson",tmpUtil.getConfigKeyValues(8));
			mav.addObject("customerInterviewStatusJson",tmpUtil.getConfigKeyValues(9));
			mav.addObject("profilesJson", tmpUtil.getProfiles());
			mav.addObject("criticalJson",tmpUtil.getConfigKeyValues(1));
			mav.addObject("intimationModeJson",tmpUtil.getConfigKeyValues(3));
			mav.addObject("requirementTypeJson",tmpUtil.getConfigKeyValues(4));
			mav.addObject("positionStatusJson",tmpUtil.getConfigKeyValues(5));
			mav.addObject("opportunityStatusJson",tmpUtil.getConfigKeyValues(6));
			mav.addObject("skillCategoryJson",tmpUtil.getConfigKeyValues(10));
			
		} else {
			mav = new ModelAndView("login");
			mav.addObject("errorMsg", true);
			mav.addObject("message", false);
		}

		return mav;

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(ModelMap map, HttpSession httpSession) {
		ModelAndView model = null;
		if(httpSession.getAttribute("user")!=null){
			httpSession.invalidate();
		    model = new ModelAndView("login");
		    model.addObject("login", new User());
			model.addObject("message", true);
		}
		return model;

	}
	
	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public ModelAndView help(ModelMap model) {
		ModelAndView mav = new ModelAndView("help");
		mav.addObject("help", new User());
		return mav;
	}
	
	@RequestMapping(value = "/contactus", method = RequestMethod.GET)
	public ModelAndView contactUs(ModelMap model) {
		ModelAndView mav = new ModelAndView("contactus");
		mav.addObject("contactus", new User());
		return mav;
	}

	public TMPUtil getTmpUtil() {
		return tmpUtil;
	}

	public void setTmpUtil(TMPUtil tmpUtil) {
		this.tmpUtil = tmpUtil;
	}

	/**
	 * Generates a key and an initialization vector (IV) with the given salt and password.
	 * <p>
	 * This method is equivalent to OpenSSL's EVP_BytesToKey function
	 * (see https://github.com/openssl/openssl/blob/master/crypto/evp/evp_key.c).
	 * By default, OpenSSL uses a single iteration, MD5 as the algorithm and UTF-8 encoded password data.
	 * </p>
	 * @param keyLength the length of the generated key (in bytes)
	 * @param ivLength the length of the generated IV (in bytes)
	 * @param iterations the number of digestion rounds 
	 * @param salt the salt data (8 bytes of data or <code>null</code>)
	 * @param password the password data (optional)
	 * @param md the message digest algorithm to use
	 * @return an two-element array with the generated key and IV
	 */
	public static byte[][] GenerateKeyAndIV(int keyLength, int ivLength, int iterations, byte[] salt, byte[] password, MessageDigest md) {

	    int digestLength = md.getDigestLength();
	    int requiredLength = (keyLength + ivLength + digestLength - 1) / digestLength * digestLength;
	    byte[] generatedData = new byte[requiredLength];
	    int generatedLength = 0;

	    try {
	        md.reset();

	        // Repeat process until sufficient data has been generated
	        while (generatedLength < keyLength + ivLength) {

	            // Digest data (last digest if available, password data, salt if available)
	            if (generatedLength > 0)
	                md.update(generatedData, generatedLength - digestLength, digestLength);
	            md.update(password);
	            if (salt != null)
	                md.update(salt, 0, 8);
	            md.digest(generatedData, generatedLength, digestLength);

	            // additional rounds
	            for (int i = 1; i < iterations; i++) {
	                md.update(generatedData, generatedLength, digestLength);
	                md.digest(generatedData, generatedLength, digestLength);
	            }

	            generatedLength += digestLength;
	        }

	        // Copy key and IV into separate byte arrays
	        byte[][] result = new byte[2][];
	        result[0] = Arrays.copyOfRange(generatedData, 0, keyLength);
	        if (ivLength > 0)
	            result[1] = Arrays.copyOfRange(generatedData, keyLength, keyLength + ivLength);

	        return result;

	    } catch (DigestException e) {
	        throw new RuntimeException(e);

	    } finally {
	        // Clean out temporary data
	        Arrays.fill(generatedData, (byte)0);
	    }
	}
}
