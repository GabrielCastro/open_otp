package ca.gabrielcastro.openotp.db

import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    internal fun provideDatabase(db: DatabaseImpl): Database {
        return db
    }
}
