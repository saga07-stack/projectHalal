package jp.co.sss.shop.util;

/**
 * リクエストURLチェック　クラス
 * @author System Shared
 *
 * ============================================================================
 * NP: यो क्लासले हरेक incoming request को URL हेरेर त्यो URL कुन प्रकारको हो
 *     (static file, login-required, admin-only, public, etc.) भनेर छुट्याउने
 *     काम गर्छ। यसलाई प्रायः Filter (जस्तै LoginFilter/AuthFilter) बाट बोलाइन्छ।
 *
 * EN: This class decides what "category" an incoming request URL belongs to
 *     (static file, needs-login, admin-only, public, etc.). It's normally
 *     called from a servlet Filter (e.g. a LoginFilter/AuthFilter) that runs
 *     before the request reaches the controller.
 *
 * NP: IMPORTANT: हरेक method ले `true` वा `false` फर्काउँछ, तर "true" को अर्थ
 *     हरेक method मा फरक फरक हुन सक्छ (कतै "यो फिल्टर गर्नुपर्छ" भन्ने अर्थ हो,
 *     कतै "यो पब्लिक हो" भन्ने)। त्यसैले हरेक method छुट्टाछुट्टै पढ्नुहोस्।
 *
 * EN: IMPORTANT: every method returns a boolean, but "true" does NOT mean the
 *     same thing in every method — in some it means "this URL should be
 *     blocked by the filter", in others "this URL is allowed / public". Read
 *     each method's own comment carefully rather than assuming a pattern.
 * ============================================================================
 */
public class URLCheck {

	/**
	 * 静的ファイルのリクエストURLであるかを判定[
	 * 
	 * @param requestURL リクエストURL
	 * @return true：静的ファイルへのリクエストURLである、false：静的ファイルへのリクエストURLではない
	 *
	 * ------------------------------------------------------------------
	 * NP: यो method ले URL CSS/JS/Image जस्तो static file हो कि होइन जाँच गर्छ।
	 *     यदि URL मा Constant.CSS_FOLDER, JS_FOLDER, वा IMAGE_FOLDER भन्ने
	 *     string कतैपनि भेटियो भने → true (static file हो, login चाहिँदैन)।
	 *     अरू सबै अवस्थामा → false।
	 *
	 *     कसरी काम गर्छ:
	 *       - OR (||) chain प्रयोग भएको छ, त्यसैले तीनमध्ये जुनसुकै एउटा मिल्यो
	 *         भने पनि true हुन्छ।
	 *       - `indexOf(...) != -1` को अर्थ: त्यो substring URL भित्र कतैपनि
	 *         भेटियो (जहाँसुकै position मा होस्)।
	 *
	 * EN: Checks whether the URL is a static asset (CSS / JS / image).
	 *     If the URL contains Constant.CSS_FOLDER, JS_FOLDER, or IMAGE_FOLDER
	 *     anywhere in it → returns true (it's a static file, no login needed).
	 *     Otherwise → false.
	 *
	 *     How it works:
	 *       - Uses an OR (||) chain, so matching ANY one of the three folder
	 *         markers is enough to return true.
	 *       - `indexOf(...) != -1` means "this substring appears somewhere in
	 *         the URL" (position doesn't matter).
	 * ------------------------------------------------------------------
	 */
	public static boolean isURLForStaticFile(String requestURL) {
		boolean isCheckURLOK = false;
		if (requestURL.indexOf(Constant.CSS_FOLDER) != -1
				|| requestURL.indexOf(Constant.JS_FOLDER) != -1
				|| requestURL.indexOf(Constant.IMAGE_FOLDER) != -1) {
			// URLのリクエスト先がフィルタ実行対象である場合
			isCheckURLOK = true;
		} else {
			// URLのリクエスト先がフィルタ実行対象ではない場合
			isCheckURLOK = false;
		}
		return isCheckURLOK;

	}

	/**
	 * システム管理者 リクエストURLがアクセス可能かを判定
	 * 
	 * @param requestURL リクエストURL
	 * @return true：アクセス可能、false：アクセス不可
	 *
	 * ------------------------------------------------------------------
	 * NP: यो method ले "System Admin (authority=0)" ले access गर्न मिल्ने URL हो
	 *     कि होइन जाँच गर्छ। true फर्किएमा access ठीक छ (allowed)।
	 *
	 *     true हुने अवस्थाहरू (OR chain, जुनसुकै एउटा मिले पुग्छ):
	 *       - static file हो
	 *       - "/login" मा अन्त्य हुन्छ
	 *       - URL भित्र "admin/menu" छ
	 *       - URL भित्र "/admin/admin_menu" छ
	 *       - URL भित्र "admin/user" छ
	 *       - "/logout" मा अन्त्य हुन्छ
	 *
	 *     माथिका कुनै पनि नमिलेमा → false (System Admin ले access गर्न पाउँदैन,
	 *     अर्थात् यो URL System Admin को दायरा भित्र पर्दैन)।
	 *
	 * EN: Checks whether this URL is one a System Admin (authority=0) is
	 *     allowed to access. true = access is OK.
	 *
	 *     Returns true if ANY of these match (OR chain):
	 *       - it's a static file
	 *       - ends with "/login"
	 *       - contains "admin/menu"
	 *       - contains "/admin/admin_menu"
	 *       - contains "admin/user"
	 *       - ends with "/logout"
	 *
	 *     If none match → false (this URL is outside the System Admin's
	 *     allowed scope).
	 * ------------------------------------------------------------------
	 */
	public static boolean isURLForSystemAdmin(String requestURL) {
		boolean isCheckURLOK = false;
		if (isURLForStaticFile(requestURL)
				|| requestURL.endsWith("/login")
				|| requestURL.indexOf("admin/menu") != -1
				|| requestURL.indexOf("/admin/admin_menu") != -1
				|| requestURL.indexOf("admin/user") != -1
				|| requestURL.endsWith("/logout")) {
			// URLのリクエスト先がフィルタ実行対象である場合
			isCheckURLOK = true;
		} else {
			// URLのリクエスト先がフィルタ実行対象ではない場合
			isCheckURLOK = false;
		}
		return isCheckURLOK;
	}

	/**
	 * 運用管理者 リクエストURLがチェック対象であるかを判定
	 * 
	 * @param requestURL リクエストURL
	 * @return true：チェック対象、false：チェック対象外
	 *
	 * ------------------------------------------------------------------
	 * NP: यो method ले "Operational Admin (authority=1)" ले access गर्न मिल्ने
	 *     URL हो कि होइन जाँच गर्छ। यसले System Admin का सबै URL पनि इनहेरिट
	 *     गर्छ (isURLForSystemAdmin लाई भित्रै call गरेर), अनि थप 3 वटा
	 *     admin-section URL पनि थप्छ।
	 *
	 *     true हुने अवस्थाहरू (OR chain):
	 *       - static file हो
	 *       - System Admin को दायरामा पर्छ (isURLForSystemAdmin == true)
	 *       - "admin/category" URL भित्र छ
	 *       - "admin/item" URL भित्र छ
	 *       - "admin/order" URL भित्र छ
	 *
	 * EN: Checks whether this URL is one an Operational Admin (authority=1)
	 *     can access. It inherits everything System Admin can access (by
	 *     calling isURLForSystemAdmin internally) plus three more admin
	 *     sections.
	 *
	 *     Returns true if ANY of these match:
	 *       - it's a static file
	 *       - it's within the System Admin scope (isURLForSystemAdmin == true)
	 *       - contains "admin/category"
	 *       - contains "admin/item"
	 *       - contains "admin/order"
	 * ------------------------------------------------------------------
	 */
	public static boolean istURLForAdmin(String requestURL) {
		boolean isCheckURLOK = false;
		if (isURLForStaticFile(requestURL)
				|| isURLForSystemAdmin(requestURL)
				|| requestURL.indexOf("admin/category") != -1
				|| requestURL.indexOf("admin/item") != -1
				|| requestURL.indexOf("admin/order") != -1) {
			// URLのリクエスト先がフィルタ実行対象である場合
			isCheckURLOK = true;
		} else {
			// URLのリクエスト先がフィルタ実行対象ではない場合
			isCheckURLOK = false;
		}
		return isCheckURLOK;

	}

	/**
	 * 一般会員 リクエストURLがチェック対象であるかを判定
	 * 
	 * @param requestURL リクエストURL
	 * @param contextPath コンテキストパス名
	 * @return true：チェック対象、false：チェック対象外
	 *
	 * ------------------------------------------------------------------
	 * NP: यो method ले "Regular Customer (authority=2)" ले access गर्न मिल्ने
	 *     URL हो कि होइन जाँच गर्छ।
	 *
	 *     ⚠️ ध्यान दिनुहोस्: यहाँ एउटा condition छ जुन धेरै "wide" (फराकिलो) छ:
	 *         requestURL.indexOf("admin") == -1
	 *     यसको अर्थ हो — "URL भित्र 'admin' शब्द कतैपनि नभएको" — जुन प्रायः
	 *     सबै non-admin URL सँग मिल्छ। त्यसैले माथिका अरू सबै individual
	 *     condition (client/item/list, items/favorite/list, items/search
	 *     आदि) व्यावहारिक रूपमा redundant (आवश्यकता नभएका) हुन सक्छन्, किनभने
	 *     "admin" नभएको जुनसुकै URL पनि यसै गरेर true हुन्छ।
	 *
	 *     true हुने अवस्थाहरू (OR chain, जुनसुकै एउटा मिले पुग्छ):
	 *       - static file हो
	 *       - contextPath + "/" मा अन्त्य हुन्छ (homepage)
	 *       - "/login" मा अन्त्य हुन्छ
	 *       - "/client/item/list/" URL भित्र छ
	 *       - "/items/favorite/list/" URL भित्र छ
	 *       - "/items/search/" URL भित्र छ
	 *       - "client" शब्द URL भित्र कतैपनि छ
	 *       - "admin" शब्द URL भित्र कतैपनि छैन  ← (सबैभन्दा फराकिलो/wide condition)
	 *       - "/logout" मा अन्त्य हुन्छ
	 *
	 * EN: Checks whether this URL is one a Regular Customer (authority=2)
	 *     can access.
	 *
	 *     ⚠️ Note: one condition here is very broad:
	 *         requestURL.indexOf("admin") == -1
	 *     This means "the word 'admin' does NOT appear anywhere in the URL" —
	 *     which is true for almost every non-admin URL. Because of the OR
	 *     chain, this single broad condition makes most of the other,
	 *     more specific conditions above it (client/item/list,
	 *     items/favorite/list, items/search, etc.) effectively redundant —
	 *     any URL without "admin" in it already returns true regardless.
	 *
	 *     Returns true if ANY of these match:
	 *       - it's a static file
	 *       - ends with contextPath + "/" (homepage)
	 *       - ends with "/login"
	 *       - contains "/client/item/list/"
	 *       - contains "/items/favorite/list/"
	 *       - contains "/items/search/"
	 *       - contains "client" anywhere
	 *       - does NOT contain "admin" anywhere  ← (the broad catch-all one)
	 *       - ends with "/logout"
	 * ------------------------------------------------------------------
	 */
	public static boolean isURLForClient(String requestURL, String contextPath) {

		boolean isCheckURLOK = false;
		if (URLCheck.isURLForStaticFile(requestURL)
				|| requestURL.endsWith(contextPath + "/")
				|| requestURL.endsWith("/login")
				|| requestURL.indexOf("/client/item/list/") != -1
				|| requestURL.indexOf("/items/favorite/list/") != -1
				|| requestURL.indexOf("/items/search/") != -1
				|| requestURL.indexOf("client") != -1
				|| requestURL.indexOf("admin") == -1
				|| requestURL.endsWith("/logout")) {
			// URLのリクエスト先がフィルタ実行対象である場合
			isCheckURLOK = true;
		} else {
			// URLのリクエスト先がフィルタ実行対象ではない場合
			isCheckURLOK = false;
		}
		return isCheckURLOK;

	}

	/**
	 *  未ログイン、非会員 リクエストURLがチェック対象であるかを判定
	 * 
	 * @param requestURL リクエストURL
	 * @param contextPath コンテキストパス名
	 * @return true：チェック対象、false：チェック対象外
	 *
	 * ------------------------------------------------------------------
	 * NP: यो method ले "logout/non-member" (login नगरेको) प्रयोगकर्ताको
	 *     हकमा, यो URL लाई login-filter ले रोक्नुपर्छ कि पर्दैन भनेर जाँच गर्छ।
	 *
	 *     ⚠️ धेरै जरुरी: यो सबै condition हरू AND (&&) chain मा जोडिएका छन्, र
	 *     हरेक एउटा condition अगाडि "!" (NOT) लागेको छ। De Morgan's law अनुसार,
	 *     "!A && !B && !C..." भनेको "!(A || B || C...)" जस्तै हो। अर्थात्:
	 *
	 *         return true  ⇔  URL तलका exemption list मध्ये कुनैसँग पनि मिलेन
	 *         return false ⇔  URL तलका exemption list मध्ये कुनै एउटासँग मिल्यो
	 *
	 *     त्यसैले:
	 *       - return TRUE  = यो URL लाई login चेक गर्नुपर्छ (login नगरे रोक्नुपर्छ)
	 *       - return FALSE = यो URL सार्वजनिक (public) हो, login नचाहिने
	 *
	 *     Exemption list (यीमध्ये जुनसुकै एउटा URL मा मिल्यो भने → public,
	 *     login चाहिँदैन):
	 *       - static file (CSS/JS/image)
	 *       - "/login" (login page आफैं)
	 *       - contextPath + "/" (homepage)
	 *       - "/client/item/list/" (product listing) — substring match
	 *       - "/client/item/detail/" (product detail) — substring match
	 *       - "/client/user/delete/" (⚠️ user delete — यो प्रायः bug हो, तल हेर्नुहोस्)
	 *       - "/items/cart/add/" (guest cart add) — substring match
	 *       - "/items/search/" (product search) — substring match
	 *       - "/items/all" (सबै item फर्काउने REST endpoint)
	 *       - Registration flow का 6 वटा URL (input, input/check, check,
	 *         check/back, complete, input/init) — यी सबै "endsWith" match हुन्
	 *
	 *     ⚠️ WARNING: "/client/user/delete/" यो list भित्र भएकोले, कुनै पनि
	 *     login नगरेको प्रयोगकर्ताले user delete गर्ने URL लाई यो filter बाट
	 *     बच्न सक्छ। यदि controller भित्र आफैं छुट्टै login/session जाँच
	 *     (जस्तै @LoginCheck annotation वा session.getAttribute("user")
	 *     null जाँच) नभएमा, यो security hole हुन सक्छ।
	 *
	 *     substring match (indexOf) vs exact-tail match (endsWith) को फरक:
	 *       - indexOf(...) == -1  → त्यो path segment URL मा कतैपनि छैन भने
	 *         मात्र यो condition true हुन्छ (parameter/ID भएका URL का लागि,
	 *         जस्तै /items/search/{keyword})
	 *       - !endsWith(...)      → ठ्याक्कै त्यही tail (अन्त्य) मा नमिलेमा
	 *         true हुन्छ (fixed, parameter नभएका URL का लागि, जस्तै /login)
	 *
	 * EN: For a NOT-logged-in / non-member user, checks whether this URL is
	 *     one that the login filter SHOULD enforce (block/redirect if the
	 *     user isn't logged in).
	 *
	 *     ⚠️ Important: all conditions are chained with AND (&&), and every
	 *     single one is negated with "!". By De Morgan's law, "!A && !B &&
	 *     !C..." is equivalent to "!(A || B || C...)". In other words:
	 *
	 *         returns TRUE  ⇔ the URL matches NONE of the exemptions below
	 *         returns FALSE ⇔ the URL matches ANY ONE of the exemptions below
	 *
	 *     So:
	 *       - TRUE  = this URL requires login (block if not authenticated)
	 *       - FALSE = this URL is public, no login needed
	 *
	 *     Exemption list (matching ANY one of these → public, no login
	 *     required):
	 *       - static file (CSS/JS/image)
	 *       - "/login" (the login page itself)
	 *       - contextPath + "/" (homepage)
	 *       - "/client/item/list/" (product listing) — substring match
	 *       - "/client/item/detail/" (product detail) — substring match
	 *       - "/client/user/delete/" (⚠️ user delete — likely a bug, see below)
	 *       - "/items/cart/add/" (guest cart add) — substring match
	 *       - "/items/search/" (product search) — substring match
	 *       - "/items/all" (REST endpoint returning all items)
	 *       - The 6 registration-flow URLs (input, input/check, check,
	 *         check/back, complete, input/init) — all exact-tail matches
	 *
	 *     ⚠️ WARNING: because "/client/user/delete/" is in this exemption
	 *     list, a logged-out user's request to that URL bypasses this
	 *     filter entirely. Unless the controller itself has its own
	 *     separate login/session check (e.g. a @LoginCheck annotation or a
	 *     manual session.getAttribute("user") == null check), this is a
	 *     potential security hole worth verifying.
	 *
	 *     Difference between substring match (indexOf) and exact-tail match
	 *     (endsWith):
	 *       - indexOf(...) == -1  → true only when that path segment does
	 *         NOT appear anywhere in the URL (used for URLs with a
	 *         parameter/ID after them, e.g. /items/search/{keyword})
	 *       - !endsWith(...)      → true when the URL does NOT end with that
	 *         exact tail (used for fixed, parameter-less URLs, e.g. /login)
	 * ------------------------------------------------------------------
	 */
	public static boolean isURLForNonLogin(String requestURL, String contextPath) {

		boolean isCheckURLOK = false;
		if (!URLCheck.isURLForStaticFile(requestURL)
				&& !requestURL.endsWith("/login")
				&& !requestURL.endsWith(contextPath + "/")
				&& requestURL.indexOf("/client/item/list/") == -1
				&& requestURL.indexOf("/client/item/detail/") == -1
				&& requestURL.indexOf("/client/user/delete/") == -1
				&& requestURL.indexOf("/items/cart/add/") == -1
			//	&& requestURL.indexOf("/items/search/") == -1
				&& !requestURL.endsWith("/items/all")
				&& requestURL.indexOf("/items/search/") != -1
				&& requestURL.indexOf("/items/favourite/list/") != -1
				&& !requestURL.endsWith("/client/user/regist/input/init")
				&& !requestURL.endsWith("/client/user/regist/input")
				&& !requestURL.endsWith("/client/user/regist/input/check")
				&& !requestURL.endsWith("/client/user/regist/check")
				&& !requestURL.endsWith("/client/user/regist/check/back")
				&& !requestURL.endsWith("/client/user/regist/complete")) {
			// URLのリクエスト先がフィルタ実行対象である場合
			isCheckURLOK = true;
		} else {
			
			// URLのリクエスト先がフィルタ実行対象ではない場合
			isCheckURLOK = false;
		}
		return isCheckURLOK;

	}

	/**
	 * カテゴリ一覧用 リクエストURLがチェック対象であるかを判定
	 *
	 * @param requestURL リクエストURL
	 * @return true：チェック対象、false：チェック対象外
	 *
	 * ------------------------------------------------------------------
	 * NP: यो method अलि फरक ढाँचामा छ — यसमा दुई ठूला भाग AND (&&) ले जोडिएका
	 *     छन्, र प्रत्येक ठूलो भाग भित्र आफ्नै OR (||) chain छ:
	 *
	 *       भाग A (दुवै सर्त पूरा हुनुपर्छ):
	 *         - static file होइन
	 *         - URL मा "/adminmenu" छैन
	 *
	 *       भाग B (कुनै एउटा सर्त पूरा भए पुग्छ):
	 *         - "/" मा अन्त्य हुन्छ, वा
	 *         - "/item/list", "/item/detail" जस्ता product-related URL हो, वा
	 *         - admin item regist/update input page हो, वा
	 *         - basket/order/checkout सम्बन्धित URL हो, वा
	 *         - user detail/regist/update/delete सम्बन्धित URL हो
	 *
	 *     दुवै भाग A र B सत्य भएमा मात्र → true (यसको मतलब: "यो URL मा
	 *     category list देखाउनुपर्छ")। अन्यथा → false।
	 *
	 *     यसको प्रयोजन प्रायः: पेजको sidebar/header मा category list देखाउने
	 *     कि नदेखाउने भनेर टेम्प्लेटले निर्णय गर्न प्रयोग हुन्छ (login/authority
	 *     सँग सम्बन्ध छैन, यो पूर्णतया फरक उद्देश्यको method हो)।
	 *
	 * EN: This method has a different shape — two big blocks joined by AND
	 *     (&&), with each block having its own internal OR (||) chain:
	 *
	 *       Block A (BOTH must be true):
	 *         - it's NOT a static file
	 *         - the URL does NOT contain "/adminmenu"
	 *
	 *       Block B (ANY ONE is enough):
	 *         - ends with "/", or
	 *         - is a product-related URL ("/item/list", "/item/detail"), or
	 *         - is the admin item regist/update input page, or
	 *         - is a basket/order/checkout-related URL, or
	 *         - is a user detail/regist/update/delete-related URL
	 *
	 *     Returns true only when BOTH Block A and Block B are true (meaning:
	 *     "this page should display the category list"). Otherwise false.
	 *
	 *     Purpose: this is typically used by templates to decide whether to
	 *     render the category list in the sidebar/header on a given page —
	 *     it's unrelated to login/authority checking; it serves a completely
	 *     different purpose from the other methods in this class.
	 * ------------------------------------------------------------------
	 */
	public static boolean isURLForMakeCategoryList(String requestURL) {

		boolean isCheckURLOK = false;
		if ((!URLCheck.isURLForStaticFile(requestURL)
				&& requestURL.indexOf("/adminmenu") == -1)
				&& (requestURL.endsWith("/")
						|| requestURL.indexOf("/item/list") != -1
						|| requestURL.indexOf("/item/detail") != -1
						|| requestURL.indexOf("/admin/item/regist/input") != -1
						|| requestURL.indexOf("/admin/item/update/input") != -1
						|| requestURL.indexOf("/client/basket") != -1
						|| requestURL.indexOf("/client/order/address") != -1
						|| requestURL.indexOf("/client/order/payment/input") != -1
						|| requestURL.indexOf("/order/list") != -1
						|| requestURL.indexOf("/client/order/check") != -1
						|| requestURL.indexOf("/order/detail") != -1
						|| requestURL.indexOf("/client/order/complete") != -1
						|| requestURL.indexOf("/client/user/detail") != -1
						|| requestURL.indexOf("/client/user/regist") != -1
						|| requestURL.indexOf("/client/user/update") != -1
						|| requestURL.indexOf("/client/user/delete") != -1)) {

			// URLのリクエスト先がフィルタ実行対象である場合
			isCheckURLOK = true;
		} else {
			// URLのリクエスト先がフィルタ実行対象ではない場合
			isCheckURLOK = false;
		}
		return isCheckURLOK;

	}
}