// Learn more about Tauri commands at https://tauri.app/develop/calling-rust/

use serde_json::json;
use serde_json::Value;

const BASE_URL: &str = "http://localhost:8080/api";

pub fn greet(name: &str) -> String {
    format!("Hello, {}! You've been greeted from Rust!", name)
}

#[tauri::command]
fn greet_command(name: &str) -> String {
    greet(name)
}

async fn create_event(title: &str, description: &str, director: &str, capacity: i32) -> Result<reqwest::Response, reqwest::Error> {
    let json = json!({
        "title": title,
        "description": description,
        "director": director,
        "capacity": capacity
    });

    let res = reqwest::Client::new()
        .post(format!("{}/events", &BASE_URL))
        .json(&json)
        .send()
        .await?;

    Ok(res)
}

#[tauri::command]
async fn create_event_command(
    title: &str,
    description: &str,
    director: &str,
    capacity: i32
) -> Result<Value, String> {
    let res = create_event(title, description, director, capacity)
        .await
        .map_err(|e| e.to_string())?;

    let body = res.json::<Value>()
        .await
        .map_err(|e| e.to_string())?;

    Ok(body)
}

#[cfg_attr(mobile, tauri::mobile_entry_point)]
pub fn run() {
    tauri::Builder::default()
        .plugin(tauri_plugin_opener::init())
        .invoke_handler(tauri::generate_handler![
            greet_command,
            create_event_command
        ])
        .run(tauri::generate_context!())
        .expect("error while running tauri application");
}
