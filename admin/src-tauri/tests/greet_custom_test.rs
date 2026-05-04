use admin_lib::greet_custom;

#[test]
fn test_greet_custom() {
    assert_eq!(
        greet_custom("Justin"),
        "Hello, Justin! You've been greeted from Rust!"
    );
}
