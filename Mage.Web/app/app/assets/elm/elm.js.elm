port module Main exposing (..)

import Html exposing (..)
import Html.App as Html
import Html.Attributes exposing (class)
import WebSocket
import Regex exposing (regex, contains)
import Json.Decode exposing (Decoder, Value, decodeString, succeed, string, (:=))
import Json.Decode.Pipeline exposing (decode, required, optional, hardcoded)
import Debug exposing (log)

type Msg = MatchEvent Model

type alias Model =
  { message : String
  }

init =
  (Model "init!", Cmd.none)

update msg model =
  case msg of
    MatchEvent thing ->
      log thing
      --if not (contains (regex "welcome") resp) && not (contains (regex "MatchEvent") resp) then
      --  log resp
      --  (decodeModel resp, Cmd.none)
      --else
      --  (model, Cmd.none) ^^

view model =
  ul [class "notes"]
    [ li [] [text (toString model.message)]
    ]

main =
  Html.program
    { init = init
    , view = view
    , update = update
    , subscriptions = subscriptions
    }


--subscriptions model =
--  WebSocket.listen "ws://localhost:3000/ws" MatchEvent

modelDecoder =
  decode Model
    |> required "message" string

decodeModel jsonString =
  log ">> decodeModel"
  log jsonString
  Result.withDefault { message = "default?" } (decodeString modelDecoder jsonString)

subscriptions model =
  match MatchEvent
  
port match : (MatchEvent -> msg) -> Sub msg
