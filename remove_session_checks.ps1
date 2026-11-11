$files = @(
    'src/main/java/jp/co/sss/shop/controller/admin/category/AdminCategoryDeleteController.java',
    'src/main/java/jp/co/sss/shop/controller/admin/category/AdminCategoryRegistController.java',
    'src/main/java/jp/co/sss/shop/controller/admin/category/AdminCategoryShowController.java',
    'src/main/java/jp/co/sss/shop/controller/admin/category/AdminCategoryUpdateController.java',
    'src/main/java/jp/co/sss/shop/controller/admin/item/AdminItemAllShowController.java',
    'src/main/java/jp/co/sss/shop/controller/admin/item/AdminItemDeleteController.java',
    'src/main/java/jp/co/sss/shop/controller/admin/item/AdminItemRegistController.java',
    'src/main/java/jp/co/sss/shop/controller/admin/item/AdminItemShowController.java',
    'src/main/java/jp/co/sss/shop/controller/admin/item/AdminItemUpdateController.java',
    'src/main/java/jp/co/sss/shop/controller/admin/order/AdminOrderProcessController.java',
    'src/main/java/jp/co/sss/shop/controller/admin/order/AdminOrderShowController.java',
    'src/main/java/jp/co/sss/shop/controller/admin/user/AdminUserDeleteController.java',
    'src/main/java/jp/co/sss/shop/controller/admin/user/AdminUserRegistController.java',
    'src/main/java/jp/co/sss/shop/controller/admin/user/AdminUserShowController.java',
    'src/main/java/jp/co/sss/shop/controller/admin/user/AdminUserUpdateController.java',
    'src/main/java/jp/co/sss/shop/controller/client/item/BasketController.java',
    'src/main/java/jp/co/sss/shop/controller/client/item/ClientFavouriteShowController.java',
    'src/main/java/jp/co/sss/shop/controller/client/order/UserOrderAddressInputController.java',
    'src/main/java/jp/co/sss/shop/controller/client/order/UserOrderHistoryController.java',
    'src/main/java/jp/co/sss/shop/controller/client/payment/PaymentController.java',
    'src/main/java/jp/co/sss/shop/controller/client/user/UserDetailsController.java',
    'src/main/java/jp/co/sss/shop/controller/login/LoginController.java'
)

foreach ($file in $files) {
    $content = Get-Content $file -Raw
    # Remove: if (userBean == null) { model.addAttribute("message", "Session time out"); return "redirect:/login"; }
    $content = $content -replace '(\s*)if\s*\(\s*userBean\s*==\s*null\s*\)\s*\{\s*model\.addAttribute\("message",\s*"Session time out"\);\s*\n\s*return\s*"redirect:/login";\s*\}', ''
    # Remove: if (session.getAttribute("user") == null) { model.addAttribute("message", "Session time out"); return "redirect:/login"; }
    $content = $content -replace '(\s*)(//\s*Check if session has timed out\s*\n)?(\s*)if\s*\(\s*session\.getAttribute\("user"\)\s*==\s*null\s*\)\s*\{\s*model\.addAttribute\("message",\s*"Session time out"\);\s*\n\s*return\s*"redirect:/login";\s*\}', ''
    # Remove: if(session.getAttribute("user") == null) { model.addAttribute("message", "Session time out"); return "redirect:/login"; }
    $content = $content -replace 'if\s*\(\s*session\.getAttribute\("user"\)\s*==\s*null\s*\)\s*\{\s*model\.addAttribute\("message",\s*"Session time out"\);\s*\n\s*return\s*"redirect:/login";\s*\}', ''
    # Remove: if(user == null) { model.addAttribute("message", "Session time out"); return "redirect:/login"; }
    $content = $content -replace 'if\s*\(\s*user\s*==\s*null\s*\)\s*\{\s*model\.addAttribute\("message",\s*"Session time out"\);\s*\n\s*return\s*"redirect:/login";\s*\}', ''
    
    Set-Content $file -Value $content
    Write-Host "Removed session checks from: $file"
}
