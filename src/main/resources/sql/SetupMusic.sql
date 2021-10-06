INSERT INTO guild_data (guild_id, music_id) values (?,?) ON CONFLICT (guild_id) DO UPDATE SET music_id = ?
