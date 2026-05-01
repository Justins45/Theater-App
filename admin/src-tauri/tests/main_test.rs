use admin_lib::greet;

#[test]
fn test_greet() {
    assert_eq!(
        greet("Justin"),
        "Hello, Justin! You've been greeted from Rust!"
    );
}
