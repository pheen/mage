$CLASSPATH << File.dirname(__FILE__) + '/config/'

require '../mage-1.4.9.jar'
require '../mage-web-client.jar'
require '../log4j-1.2.17.jar'

import 'mage.web.Client'
import 'mage.game.match.MatchOptions'
import 'mage.constants.MultiplayerAttackOption'
import 'mage.constants.RangeOfInfluence'
import 'mage.constants.MatchTimeLimit'
import 'mage.cards.decks.importer.DeckImporterUtil'

class RubyClient
  def initialize(username)
    @client = Java::MageWeb::Client.new(username, self)
    @client.connect
    @session = @client.get_session

    room_id = @session.get_main_room_id
    tables = @session.get_tables(room_id)

    game_type = @session.get_game_types.first # Two Player Duel
    options = Java::MageGameMatch::MatchOptions.new('match name', game_type.get_name)

    # Mage.Client/src/main/java/mage/client/dialog/NewTableDialog.java:382
    options.get_player_types.add('Human')
    options.get_player_types.add('Computer - mad')
    options.set_deck_type('Limited')
    options.set_attack_option(Java::MageConstants::MultiplayerAttackOption::LEFT)
    options.set_range(Java::MageConstants::RangeOfInfluence::ALL)
    options.set_wins_needed(1)
    options.set_match_time_limit(Java::MageConstants::MatchTimeLimit.values.first)

    @table = @session.create_table(room_id, options)
    @deck = Java::MageCardsDecksImporter::DeckImporterUtil.import_deck('Modern Jund.dck')
    @deck2 = Java::MageCardsDecksImporter::DeckImporterUtil.import_deck('Modern Jund.dck')
    joined_status = @session.join_table(
      room_id,
      @table.get_table_id,
      username,
      'Human',
      1, # skill ??
      @deck,
      '' # password
    )
    joined_status_ai = @session.join_table(
      room_id,
      @table.get_table_id,
      'Computer',
      'Computer - mad',
      5, # skill, AI prob ??
      @deck2,
      '' # password
    )

    started = @session.start_match(room_id, @table.get_table_id)
  end

  def processCallback(callback)
    puts 'processCallback'
    puts callback.get_method

    method = callback.get_method

    case method
    when 'joinedTable'
      # showTableWaitingDialog
    when 'showUserMessage'
      # erorr message
    when 'startGame'
      message = callback.data
      @game_id = message.game_id
      # GameManager.getInstance().setCurrentPlayerUUID(message.getPlayerId());

      @session.join_game(@game_id)
    when 'gameInit'
      @game = callback.data
      # binding.pry
    when 'gameAsk'
      message = callback.data
      @game_game_ask = message.game_view

      binding.pry

      @session.send_player_boolean(@game_id, rand > 0.50 ? true : false)
    when 'gameUpdate'
      # binding.pry
    when 'gameInform'
      # binding.pry
    when 'gameTarget'
      # binding.pry
      @session.send_player_boolean(@game_id, rand > 0.50 ? true : false)
    end
  end

  def connected(message) # String 
    puts 'connected'
    # binding.pry
  end

  def disconnected(error_call) # boolean 
    puts 'disconnected'
    # binding.pry
  end

  def showMessage(message) # String 
    puts 'showMessage'
    # binding.pry
  end

  def showError(message) # String 
    puts 'showError'
    # binding.pry
  end
end
